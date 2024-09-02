package com.gabrielluciano.creditassessmentapi.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gabrielluciano.creditassessmentapi.domain.*;
import com.gabrielluciano.creditassessmentapi.infra.clients.CardResourceClient;
import com.gabrielluciano.creditassessmentapi.infra.clients.CustomerResourceClient;
import com.gabrielluciano.creditassessmentapi.infra.mqueue.CardIssuanceRequestPublisher;
import com.gabrielluciano.creditassessmentapi.services.exceptions.CardRequestErrorException;
import com.gabrielluciano.creditassessmentapi.services.exceptions.CustomerDataNotFoundException;
import com.gabrielluciano.creditassessmentapi.services.exceptions.MicroserviceCommunicationErrorException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreditAssessmentService {

    private final CustomerResourceClient customerClient;
    private final CardResourceClient cardClient;
    private final CardIssuanceRequestPublisher cardIssuancePublisher;

    public CustomerStatus getCustomerStatus(String cpf)
            throws CustomerDataNotFoundException, MicroserviceCommunicationErrorException {
        try {
            return tryToGetCustomerStatus(cpf);
        } catch (FeignException.NotFound ex) {
            throw new CustomerDataNotFoundException(cpf, ex);
        } catch (FeignException.FeignClientException ex) {
            throw new MicroserviceCommunicationErrorException(ex.getMessage(), ex.status(), ex);
        }
    }

    private CustomerStatus tryToGetCustomerStatus(String cpf) {
        ResponseEntity<CustomerData> customerDataResponse = customerClient.customerData(cpf);
        ResponseEntity<List<CustomerCard>> customerCardsResponse = cardClient.getCardsByCpf(cpf);

        return CustomerStatus.builder()
                .customer(customerDataResponse.getBody())
                .cards(customerCardsResponse.getBody())
                .build();
    }

    public CreditAssessment doCreditAssessment(AssessmentData data)
            throws CustomerDataNotFoundException, MicroserviceCommunicationErrorException {
        try {
            return tryToDoCreditAssessment(data);
        } catch (FeignException.NotFound ex) {
            throw new CustomerDataNotFoundException(data.cpf(), ex);
        } catch (FeignException.FeignClientException ex) {
            throw new MicroserviceCommunicationErrorException(ex.getMessage(), ex.status(), ex);
        }
    }

    private CreditAssessment tryToDoCreditAssessment(AssessmentData data) {
        ResponseEntity<CustomerData> customerDataResponse = customerClient.customerData(data.cpf());
        ResponseEntity<List<Card>> availableCardsResponse = cardClient.getAvailableCardsForIncome(data.income());

        CustomerData customerData = customerDataResponse.getBody();
        List<Card> availableCards = availableCardsResponse.getBody();

        if (customerData == null || availableCards == null)
            return new CreditAssessment(Collections.emptyList());

        List<CustomerCard> customerCards = getCustomerCards(availableCards, customerData.birthday());
        return new CreditAssessment(customerCards);
    }

    private List<CustomerCard> getCustomerCards(List<Card> availableCards, LocalDate customerBirthday) {
        return availableCards.stream().map(card -> {
            BigDecimal approvedLimit = calculateApprovedLimitForCustomer(card.defaultLimit(), customerBirthday);
            return new CustomerCard(card.name(), card.brand(), approvedLimit);
        }).toList();
    }

    private BigDecimal calculateApprovedLimitForCustomer(BigDecimal cardDefaultLimit, LocalDate customerBirthday) {
        int age = Period.between(customerBirthday, LocalDate.now()).getYears();
        BigDecimal cardLimitFactor = BigDecimal.valueOf((double) age / 10);
        return cardDefaultLimit.multiply(cardLimitFactor);
    }

    public CardRequestProtocol requestCardIssuance(CardIssuanceRequestData data) {
        try {
            return tryToRequestCardIssuance(data);
        } catch (Exception ex) {
            throw new CardRequestErrorException(ex.getMessage(), ex);
        }
    }

    private CardRequestProtocol tryToRequestCardIssuance(CardIssuanceRequestData data) throws JsonProcessingException {
        cardIssuancePublisher.requestCard(data);
        String protocol = UUID.randomUUID().toString();
        return new CardRequestProtocol(protocol);
    }
}

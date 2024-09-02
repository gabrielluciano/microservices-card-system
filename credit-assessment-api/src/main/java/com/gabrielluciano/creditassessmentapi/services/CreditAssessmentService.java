package com.gabrielluciano.creditassessmentapi.services;

import com.gabrielluciano.creditassessmentapi.domain.CustomerCard;
import com.gabrielluciano.creditassessmentapi.domain.CustomerData;
import com.gabrielluciano.creditassessmentapi.domain.CustomerStatus;
import com.gabrielluciano.creditassessmentapi.infra.clients.CardResourceClient;
import com.gabrielluciano.creditassessmentapi.infra.clients.CustomerResourceClient;
import com.gabrielluciano.creditassessmentapi.services.exceptions.CustomerDataNotFoundException;
import com.gabrielluciano.creditassessmentapi.services.exceptions.MicroserviceCommunicationErrorException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditAssessmentService {

    private final CustomerResourceClient customerClient;
    private final CardResourceClient cardClient;

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

    public CustomerStatus tryToGetCustomerStatus(String cpf) {
        ResponseEntity<CustomerData> customerDataResponse = customerClient.customerData(cpf);
        ResponseEntity<List<CustomerCard>> customerCardsResponse = cardClient.getCardsByCpf(cpf);

        return CustomerStatus.builder()
                .customer(customerDataResponse.getBody())
                .cards(customerCardsResponse.getBody())
                .build();
    }
}

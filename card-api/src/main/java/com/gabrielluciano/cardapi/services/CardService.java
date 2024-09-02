package com.gabrielluciano.cardapi.services;

import com.gabrielluciano.cardapi.domain.Card;
import com.gabrielluciano.cardapi.domain.CustomerCard;
import com.gabrielluciano.cardapi.infra.repository.CardRepository;
import com.gabrielluciano.cardapi.infra.repository.CustomerCardRepository;
import com.gabrielluciano.cardapi.services.dto.CardIssuanceRequestDTO;
import com.gabrielluciano.cardapi.services.dto.CreateCardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final CustomerCardRepository customerCardRepository;

    @Transactional
    public Card save(CreateCardDTO createCardDTO) {
        return cardRepository.save(createCardDTO.toModel());
    }

    public List<Card> getAvailableCardsForIncome(Long income) {
        return cardRepository.findByIncomeLessThanEqual(BigDecimal.valueOf(income));
    }

    @Transactional
    public void issueCard(CardIssuanceRequestDTO dto) {
        Card card = cardRepository.findById(dto.cardId()).orElseThrow();
        CustomerCard customerCard = new CustomerCard();
        customerCard.setCard(card);
        customerCard.setCpf(dto.cpf());
        customerCard.setLimit(dto.approvedLimit());
        customerCardRepository.save(customerCard);
    }
}

package com.gabrielluciano.cardapi.services;

import com.gabrielluciano.cardapi.domain.Card;
import com.gabrielluciano.cardapi.infra.repository.CardRepository;
import com.gabrielluciano.cardapi.services.dto.CreateCardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository repository;

    @Transactional
    public Card save(CreateCardDTO createCardDTO) {
        return repository.save(createCardDTO.toModel());
    }

    public List<Card> getAvailableCardsForIncome(Long income) {
        return repository.findByIncomeLessThanEqual(BigDecimal.valueOf(income));
    }
}

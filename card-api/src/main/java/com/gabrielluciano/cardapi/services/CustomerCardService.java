package com.gabrielluciano.cardapi.services;

import com.gabrielluciano.cardapi.infra.repository.CustomerCardRepository;
import com.gabrielluciano.cardapi.services.dto.CustomerCardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerCardService {

    private final CustomerCardRepository repository;

    public List<CustomerCardDTO> getCardsByCpf(String cpf) {
        return repository.findByCpf(cpf).stream()
                .map(CustomerCardDTO::fromModel)
                .toList();
    }
}

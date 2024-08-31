package com.gabrielluciano.customerapi.services;

import com.gabrielluciano.customerapi.domain.Customer;
import com.gabrielluciano.customerapi.infra.repository.CustomerRepository;
import com.gabrielluciano.customerapi.services.dto.CreateCustomerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;

    @Transactional
    public Customer save(CreateCustomerDTO createCustomerDTO) {
        return repository.save(createCustomerDTO.toModel());
    }

    public Optional<Customer> getByCpf(String cpf) {
        return repository.findByCpf(cpf);
    }
}

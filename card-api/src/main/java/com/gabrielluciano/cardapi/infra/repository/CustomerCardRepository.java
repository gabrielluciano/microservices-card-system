package com.gabrielluciano.cardapi.infra.repository;

import com.gabrielluciano.cardapi.domain.CustomerCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerCardRepository extends JpaRepository<CustomerCard, Long> {

    List<CustomerCard> findByCpf(String cpf);
}

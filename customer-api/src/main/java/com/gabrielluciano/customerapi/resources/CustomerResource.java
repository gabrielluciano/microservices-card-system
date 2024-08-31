package com.gabrielluciano.customerapi.resources;

import com.gabrielluciano.customerapi.domain.Customer;
import com.gabrielluciano.customerapi.services.CustomerService;
import com.gabrielluciano.customerapi.services.dto.CreateCustomerDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("customers")
@RequiredArgsConstructor
@Slf4j
public class CustomerResource {

    private final CustomerService service;

    @GetMapping
    public String status() {
        log.info("Customer service status requested");
        return "ok";
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody CreateCustomerDTO createCustomerDTO) {
        Customer customer = service.save(createCustomerDTO);
        log.info("Created customer with id '{}'", customer.getId());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .query("cpf={cpf}")
                .buildAndExpand(customer.getCpf())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(params = "cpf")
    public ResponseEntity<Customer> customerData(@RequestParam("cpf") String cpf) {
        return service.getByCpf(cpf)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

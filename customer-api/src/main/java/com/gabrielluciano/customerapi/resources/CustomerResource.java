package com.gabrielluciano.customerapi.resources;

import com.gabrielluciano.customerapi.domain.Customer;
import com.gabrielluciano.customerapi.services.CustomerService;
import com.gabrielluciano.customerapi.services.dto.CreateCustomerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("customers")
@RequiredArgsConstructor
public class CustomerResource {

    private final CustomerService service;

    @GetMapping
    public String status() {
        return "ok";
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody CreateCustomerDTO createCustomerDTO) {
        Customer customer = service.save(createCustomerDTO);
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

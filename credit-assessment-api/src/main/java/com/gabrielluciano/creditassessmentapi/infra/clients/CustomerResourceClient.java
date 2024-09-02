package com.gabrielluciano.creditassessmentapi.infra.clients;

import com.gabrielluciano.creditassessmentapi.domain.CustomerData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "customer-api", path = "/customers")
public interface CustomerResourceClient {

    @GetMapping(params = "cpf")
    ResponseEntity<CustomerData> customerData(@RequestParam("cpf") String cpf);
}

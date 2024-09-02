package com.gabrielluciano.creditassessmentapi.resources;

import com.gabrielluciano.creditassessmentapi.domain.AssessmentData;
import com.gabrielluciano.creditassessmentapi.services.CreditAssessmentService;
import com.gabrielluciano.creditassessmentapi.services.exceptions.CustomerDataNotFoundException;
import com.gabrielluciano.creditassessmentapi.services.exceptions.MicroserviceCommunicationErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("credit-assessments")
@RequiredArgsConstructor
@Slf4j
public class CreditAssessmentController {

    private final CreditAssessmentService service;

    @GetMapping
    public String status() {
        log.info("Credit Assessment service status requested");
        return "ok";
    }

    @GetMapping(value = "customer-status", params = "cpf")
    public ResponseEntity<?> checkCustomerStatus(@RequestParam("cpf") String cpf) {
        try {
            return ResponseEntity.ok(service.getCustomerStatus(cpf));
        } catch (CustomerDataNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (MicroserviceCommunicationErrorException ex) {
            log.error("Microservice communication error", ex);
            return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> doCreditAssessment(@RequestBody AssessmentData data) {
        try {
            return ResponseEntity.ok(service.doCreditAssessment(data));
        } catch (CustomerDataNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (MicroserviceCommunicationErrorException ex) {
            log.error("Microservice communication error", ex);
            return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
        }
    }
}

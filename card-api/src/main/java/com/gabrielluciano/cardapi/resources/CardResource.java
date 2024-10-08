package com.gabrielluciano.cardapi.resources;

import com.gabrielluciano.cardapi.domain.Card;
import com.gabrielluciano.cardapi.services.CardService;
import com.gabrielluciano.cardapi.services.CustomerCardService;
import com.gabrielluciano.cardapi.services.dto.CreateCardDTO;
import com.gabrielluciano.cardapi.services.dto.CustomerCardDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cards")
@RequiredArgsConstructor
@Slf4j
public class CardResource {

    private final CardService cardService;
    private final CustomerCardService customerCardService;

    @GetMapping
    public String status() {
        log.info("Card service status requested");
        return "ok";
    }

    @PostMapping
    public ResponseEntity<Card> save(@RequestBody CreateCardDTO createCardDTO) {
        Card card = cardService.save(createCardDTO);
        log.info("Created card with id '{}'", card.getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(params = "income")
    public ResponseEntity<List<Card>> getAvailableCardsForIncome(@RequestParam("income") Long income) {
        return ResponseEntity.ok(cardService.getAvailableCardsForIncome(income));
    }

    @GetMapping(params = "cpf")
    public ResponseEntity<List<CustomerCardDTO>> getCardsByCpf(@RequestParam("cpf") String cpf) {
        return ResponseEntity.ok(customerCardService.getCardsByCpf(cpf));
    }
}

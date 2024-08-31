package com.gabrielluciano.cardapi.resources;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cards")
@Slf4j
public class CardResource {

    @GetMapping
    public String status() {
        log.info("Card service status requested");
        return "ok";
    }
}

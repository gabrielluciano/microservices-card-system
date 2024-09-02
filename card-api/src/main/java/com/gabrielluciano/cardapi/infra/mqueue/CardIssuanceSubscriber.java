package com.gabrielluciano.cardapi.infra.mqueue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabrielluciano.cardapi.services.CardService;
import com.gabrielluciano.cardapi.services.dto.CardIssuanceRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CardIssuanceSubscriber {

    private final CardService cardService;

    @RabbitListener(queues = "${mq.queues.card-issuance}")
    public void receiveIssuanceRequest(@Payload String payload) {
        log.info("Received card issuance request. Payload: {}", payload);
        try {
            CardIssuanceRequestDTO cardIssuanceRequestDTO = new ObjectMapper().readValue(payload, CardIssuanceRequestDTO.class);
            cardService.issueCard(cardIssuanceRequestDTO);
        } catch (JsonProcessingException ex) {
            log.error("Error deserializing issuance request", ex);
        } catch (Exception ex) {
            log.error("Error processing issuance request", ex);
        }
    }
}

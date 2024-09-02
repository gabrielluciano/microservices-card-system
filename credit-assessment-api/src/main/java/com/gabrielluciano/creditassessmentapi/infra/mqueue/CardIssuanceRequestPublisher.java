package com.gabrielluciano.creditassessmentapi.infra.mqueue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabrielluciano.creditassessmentapi.domain.CardIssuanceRequestData;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CardIssuanceRequestPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final Queue cardIssuanceQueue;

    public void requestCard(CardIssuanceRequestData data) throws JsonProcessingException {
        rabbitTemplate.convertAndSend(cardIssuanceQueue.getName(), asJsonString(data));
    }

    private String asJsonString(CardIssuanceRequestData data) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(data);
    }
}

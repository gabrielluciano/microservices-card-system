package com.gabrielluciano.creditassessmentapi.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageQueueConfig {

    @Value("${mq.queues.card-issuance}")
    private String cardIssuanceQueue;

    @Bean
    public Queue cardIssuanceQueue() {
        boolean isDurable = true;
        return new Queue(cardIssuanceQueue, isDurable);
    }
}

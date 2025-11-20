package com.devsu.hackerearth.backend.client.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.client.config.RabbitConfig;
import com.devsu.hackerearth.backend.client.model.dto.ClientCreatedEvent;

@Service
public class ClientEventProducer {

    private final AmqpTemplate amqpTemplate;
    private final Logger logger = LoggerFactory.getLogger(ClientEventProducer.class);

    public ClientEventProducer(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void publishClientCreated(Long id, String name) {
        ClientCreatedEvent event = new ClientCreatedEvent(id, name);
        amqpTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY, event);
        logger.info("Evento publicado {} - {}", event.getId(), event.getName());
    }

}

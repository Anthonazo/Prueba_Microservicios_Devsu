package com.devsu.hackerearth.backend.account.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.config.RabbitConfig;
import com.devsu.hackerearth.backend.account.model.ClientSnapshot;
import com.devsu.hackerearth.backend.account.model.dto.ClientCreatedEventimplements;
import com.devsu.hackerearth.backend.account.repository.ClientSnapshotRepository;

@Service
public class ClientEventListener {

    private final ClientSnapshotRepository clientSnapshotRepository;
    private final Logger logger = LoggerFactory.getLogger(ClientEventListener.class);

    public ClientEventListener(ClientSnapshotRepository clientSnapshotRepository) {
        this.clientSnapshotRepository = clientSnapshotRepository;
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void handleClientCreated(ClientCreatedEventimplements event) {
        ClientSnapshot snapshot = new ClientSnapshot();
        snapshot.setId(event.getId());
        snapshot.setName(event.getName());
        clientSnapshotRepository.save(snapshot);
        logger.info("Creado entidad en account" + snapshot.getId() + " " + snapshot.getName());
    }

}

package com.devsu.hackerearth.backend.client;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.devsu.hackerearth.backend.client.controller.ClientController;
import com.devsu.hackerearth.backend.client.model.Client;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.repository.ClientRepository;
import com.devsu.hackerearth.backend.client.service.ClientService;

@SpringBootTest
public class sampleTest {

    @Autowired
    private ClientRepository clientRepository;

    private ClientService clientService = mock(ClientService.class);
    private ClientController clientController = new ClientController(clientService);

    @Test
    void createClientTest() {

        ClientDto newClient = new ClientDto(1L, "Dni", "Name", "Password", "Gender", 1, "Address", "9999999999", true);
        ClientDto createdClient = new ClientDto(1L, "Dni", "Name", "Password", "Gender", 1, "Address", "9999999999",
                true);
        when(clientService.create(newClient)).thenReturn(createdClient);

        ResponseEntity<ClientDto> response = clientController.create(newClient);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdClient, response.getBody());
    }

    // TEST F5

    @Test
    void clientEntityFullTest() {
        Client client = new Client();
        client.setId(1L);
        client.setName("Anthony");
        client.setDni("1234567890");
        client.setGender("Male");
        client.setAge(30);
        client.setAddress("123 Main Street");
        client.setPhone("+593987654321");
        client.setPassword("miPasswordSeguro");
        client.setActive(true);

        assertAll(
                () -> assertEquals(1L, client.getId()),
                () -> assertEquals("Anthony", client.getName()),
                () -> assertEquals("1234567890", client.getDni()),
                () -> assertEquals("Male", client.getGender()),
                () -> assertEquals(30, client.getAge()),
                () -> assertEquals("123 Main Street", client.getAddress()),
                () -> assertEquals("+593987654321", client.getPhone()),
                () -> assertEquals("miPasswordSeguro", client.getPassword()),
                () -> assertTrue(client.isActive()));
    }

    // TEST F6

    @Test
    @Transactional
    void clientRepositoryIntegrationTest() {
        Client client = new Client();
        client.setName("Anthony");
        client.setDni("123456");
        client.setGender("Male");
        client.setAge(30);
        client.setAddress("123 Main St");
        client.setPhone("+593987654321");
        client.setPassword("securePass");
        client.setActive(true);

        Client saved = clientRepository.save(client);

        Client found = clientRepository.findById(saved.getId()).orElse(null);

        assertEquals(saved.getId(), found.getId());
        assertEquals("Anthony", found.getName());
        assertEquals(true, found.isActive());
    }

}

package com.devsu.hackerearth.backend.client.utils.mappers;

import org.springframework.stereotype.Component;

import com.devsu.hackerearth.backend.client.model.Client;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;

@Component
public class ClientMapper {

    public Client clientDtoToEntity(ClientDto clientDto) {
        if (clientDto == null) {
            return null;
        }
        Client entity = new Client();
        entity.setName(clientDto.getName());
        entity.setDni(clientDto.getDni());
        entity.setGender(clientDto.getGender());
        entity.setAge(clientDto.getAge());
        entity.setAddress(clientDto.getAddress());
        entity.setPhone(clientDto.getPhone());
        entity.setPassword(clientDto.getPassword());
        entity.setActive(clientDto.isActive());

        return entity;
    }

    public void updateEntity(ClientDto clientDto, Client entity){
        entity.setName(clientDto.getName());
        entity.setDni(clientDto.getDni());
        entity.setGender(clientDto.getGender());
        entity.setAge(clientDto.getAge());
        entity.setAddress(clientDto.getAddress());
        entity.setPhone(clientDto.getPhone());
        entity.setPassword(clientDto.getPassword());
        entity.setActive(clientDto.isActive());
    }

    public ClientDto clientEntitytoDto(Client client) {
        if (client == null) {
            return null;
        }

        ClientDto clientDto = new ClientDto(
                client.getId(), client.getDni(), client.getName(),
                client.getPassword(), client.getGender(),
                client.getAge(), client.getAddress(),
                client.getPhone(), client.isActive());

        return clientDto;
    }
}

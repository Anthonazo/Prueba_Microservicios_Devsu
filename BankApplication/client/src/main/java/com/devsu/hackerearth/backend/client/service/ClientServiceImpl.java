package com.devsu.hackerearth.backend.client.service;

import java.lang.StackWalker.Option;
import java.util.List;
import java.util.Optional;

import javax.swing.text.html.parser.Entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.client.model.Client;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;
import com.devsu.hackerearth.backend.client.repository.ClientRepository;
import com.devsu.hackerearth.backend.client.utils.mappers.ClientMapper;

@Service
public class ClientServiceImpl implements ClientService {

	private final ClientRepository clientRepository;
	private final ClientMapper clientMapper;
	private final ClientEventProducer clientEventProducer;

	public ClientServiceImpl(ClientRepository clientRepository,
			ClientMapper clientMapper,
			ClientEventProducer clientEventProducer) {
		this.clientRepository = clientRepository;
		this.clientMapper = clientMapper;
		this.clientEventProducer = clientEventProducer;
	}

	@Override
	public List<ClientDto> getAll() {
		List<ClientDto> entities = clientRepository.findAllBy(ClientDto.class);
		return entities;
	}

	@Override
	public ClientDto getById(Long id) {
		Client entity = clientRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Valor no encontrado"));
		return clientMapper.clientEntitytoDto(entity);
	}

	@Override
	public ClientDto create(ClientDto clientDto) {
		Client entity = clientRepository.save(clientMapper.clientDtoToEntity(clientDto));
		clientEventProducer.publishClientCreated(entity.getId(), entity.getName());
		return clientMapper.clientEntitytoDto(entity);
	}

	@Override
	public ClientDto update(ClientDto clientDto) {
		
		Client entity = clientRepository.findById(clientDto.getId())
				.orElseThrow(() -> new RuntimeException("Valor no existe"));
		clientMapper.updateEntity(clientDto, entity);
		Client update = clientRepository.save(entity);
		return clientMapper.clientEntitytoDto(update);
	}

	@Override
	public ClientDto partialUpdate(Long id, PartialClientDto partialClientDto) {
		Client entity = clientRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Valor no encontrado"));
		entity.setActive(partialClientDto.isActive());
		clientRepository.save(entity);
		return clientMapper.clientEntitytoDto(entity);
	}

	@Override
	public void deleteById(Long id) {
		clientRepository.deleteById(id);
	}

}

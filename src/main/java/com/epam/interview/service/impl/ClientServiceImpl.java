package com.epam.interview.service.impl;

import com.epam.interview.dto.ClientDto;
import com.epam.interview.exception.NoClientExistsException;
import com.epam.interview.mapper.ClientMapper;
import com.epam.interview.model.Client;
import com.epam.interview.model.Risk;
import com.epam.interview.repository.ClientRepository;
import com.epam.interview.service.ClientService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    @Override
    public List<ClientDto> getAll() {
        final List<Client> clients = clientRepository.findAll();
        return clients.stream()
                .map(clientMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ClientDto get(Long id) {
        final Client clientEntity = this.getOrThrow(id);
        return clientMapper.toDto(clientEntity);
    }

    @Override
    public ClientDto create(ClientDto clientDto) {
        final Client clientEntity = clientMapper.toEntityExcludeId(clientDto);
        return save(clientEntity);
    }

    @Override
    public ClientDto update(ClientDto client) {
        final Client clientEntity = this.getOrThrow(client.getId());
        clientEntity.setRiskProfile(client.getRiskProfile());
        return save(clientEntity);
    }

    @Override
    public void delete(Long id) {
        final Client clientEntity = this.getOrThrow(id);
        clientRepository.delete(clientEntity);
    }

    @Override
    public List<ClientDto> merge(Set<Long> ids) {
        final List<Client> requestedClients = clientRepository.findAllById(ids);
        if (requestedClients.size() > 1) {
            final Risk maxRisk = this.getMaxRisk(requestedClients);
            List<Client> updatableClients = requestedClients.stream()
                    .filter(client -> !client.getRiskProfile().equals(maxRisk))
                    .collect(Collectors.toList());
            updatableClients.forEach(client -> client.setRiskProfile(maxRisk));
            return clientRepository.saveAll(updatableClients)
                    .stream().map(clientMapper::toDto).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private Risk getMaxRisk(List<Client> requestedClients) {
        return requestedClients.stream()
                .map(Client::getRiskProfile)
                .max(Enum::compareTo)
                .orElseThrow(RuntimeException::new);
    }

    private Client getOrThrow(Long id) {
        return clientRepository.findById(id).orElseThrow(NoClientExistsException::new);
    }

    private ClientDto save(Client clientEntity) {
        Client savedEntity = clientRepository.save(clientEntity);
        return clientMapper.toDto(savedEntity);
    }

}

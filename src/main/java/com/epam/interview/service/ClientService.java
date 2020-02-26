package com.epam.interview.service;

import com.epam.interview.dto.ClientDto;
import com.epam.interview.model.Client;

import java.util.List;
import java.util.Set;

public interface ClientService {

    List<ClientDto> getAll();

    ClientDto get(Long id);

    ClientDto create(ClientDto client);

    ClientDto update(ClientDto client);

    void delete(Long id);

    List<ClientDto> merge(Set<Long> ids);

}

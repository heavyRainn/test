package com.epam.interview.service;

import com.epam.interview.dto.ClientDto;
import com.epam.interview.exception.NoClientExistsException;
import com.epam.interview.mapper.ClientMapper;
import com.epam.interview.model.Client;
import com.epam.interview.model.Risk;
import com.epam.interview.repository.ClientRepository;
import com.epam.interview.service.impl.ClientServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
    public void testGetAll(){
        ClientDto clientDto = new ClientDto(1L, Risk.HIGH);
        Client clientEntity = new Client(clientDto.getId(), clientDto.getRiskProfile());

        List<Client> clientEntities = Collections.singletonList(clientEntity);

        when(clientRepository.findAll()).thenReturn(clientEntities);
        when(clientMapper.toDto(any())).thenReturn(clientDto);
        List<ClientDto> clients = clientService.getAll();

        verify(clientRepository).findAll();
        Assert.assertFalse(clients.isEmpty());
    }

    @Test
    public void testGet(){
        Client client = new Client(1L, Risk.HIGH);
        Optional<Client> opt = Optional.of(client);

        when(clientRepository.findById(anyLong())).thenReturn(opt);
        clientService.get(anyLong());

        verify(clientRepository).findById(anyLong());
    }

    @Test(expected = NoClientExistsException.class)
    public void testGetNoClientExistsException(){
        when(clientService.get(anyLong())).thenReturn(any());
        clientService.get(anyLong());
    }

    @Test
    public void testCreate(){
        Client client = new Client(1L, Risk.HIGH);
        when(clientRepository.save(any())).thenReturn(client);
        clientService.create(any());

        verify(clientRepository).save(any());
    }

    @Test
    public void testUpdate(){
        ClientDto clientDto = new ClientDto(1L, Risk.HIGH);
        Client client = new Client(1L, Risk.HIGH);
        Optional<Client> opt = Optional.of(client);

        when(clientRepository.findById(anyLong())).thenReturn(opt);
        when(clientRepository.save(any())).thenReturn(client);

        clientService.update(clientDto);

        verify(clientRepository).save(any());
    }

    @Test(expected = NoClientExistsException.class)
    public void testUpdateNoClientExistsException(){
        ClientDto clientDto = new ClientDto(1L, Risk.HIGH);
        Client client = new Client(1L, Risk.HIGH);

        clientService.update(clientDto);

        verify(clientRepository).save(any());
    }

    @Test
    public void testDelete(){
        Client client = new Client(1L, Risk.HIGH);
        when(clientRepository.findById(any())).thenReturn(Optional.of(client));
        clientService.delete(any());

        verify(clientRepository).delete(any());
    }

    @Test(expected = NoClientExistsException.class)
    public void testDeleteNoClientExistsException(){
        clientService.delete(any());
    }

    @Test
    public void testMerge(){
        List<Client> clients = new ArrayList<>();
        Client client = new Client(1L, Risk.HIGH);
        Client client1 = new Client(2L, Risk.LOW);
        clients.add(client);
        clients.add(client1);

        ClientDto clientDto = new ClientDto(1L, Risk.HIGH);

        Set<Long> ids = new HashSet<>();
        ids.add(1L);
        ids.add(2L);

        when(clientRepository.findAllById(anySet())).thenReturn(clients);

        clientService.merge(ids);

        verify(clientRepository).findAllById(anySet());
        verify(clientRepository).saveAll(anyList());
    }

}

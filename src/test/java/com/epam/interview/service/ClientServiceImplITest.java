package com.epam.interview.service;

import com.epam.interview.dto.ClientDto;
import com.epam.interview.exception.NoClientExistsException;
import com.epam.interview.model.Risk;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ClientServiceImplITest {

    @Autowired
    private ClientService clientService;

    @Test
    public void testGet() {
        ClientDto client = new ClientDto();
        client.setRiskProfile(Risk.HIGH);
        client.setId(1L);
        ClientDto client1 = new ClientDto();
        client1.setRiskProfile(Risk.LOW);
        client1.setId(2L);

        clientService.create(client);
        clientService.create(client1);

        ClientDto resultClient = clientService.get(1L);
        ClientDto resultClient1 = clientService.get(2L);

        Assert.assertEquals(client, resultClient);
        Assert.assertEquals(client1, resultClient1);
    }

    @Test(expected = NoClientExistsException.class)
    public void testGetNoSuchClientExpected() {
        clientService.get(1L);
    }

    @Test
    public void testCreate(){
        ClientDto client = new ClientDto();
        client.setRiskProfile(Risk.NORMAL);

        clientService.create(client);

        client.setId(1L);

        Assert.assertEquals(client, clientService.get(1L));

    }

    @Test
    public void testUpdate() {
        ClientDto client = new ClientDto();
        client.setRiskProfile(Risk.HIGH);

        ClientDto resultClientDto = clientService.create(client);

        ClientDto clientToUpdate = new ClientDto();
        clientToUpdate.setRiskProfile(Risk.LOW);
        clientToUpdate.setId(1L);

        resultClientDto = clientService.update(clientToUpdate);

        client.setRiskProfile(Risk.LOW);
        Assert.assertEquals(clientToUpdate, resultClientDto);
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void testUpdateInvalidDataExpected() {
        ClientDto clientToUpdate = new ClientDto();
        clientToUpdate.setRiskProfile(Risk.LOW);
        clientService.update(clientToUpdate);
    }

    @Test
    public void testGetAll() {
        ClientDto client = new ClientDto();
        client.setRiskProfile(Risk.HIGH);
        client.setId(1L);
        ClientDto client1 = new ClientDto();
        client1.setRiskProfile(Risk.LOW);
        client1.setId(2L);

        clientService.create(client);
        clientService.create(client1);

        List<ClientDto> clients = clientService.getAll();
        Assert.assertEquals(2, clients.size());
    }

    @Test
    public void testDelete() {
        ClientDto client = new ClientDto();
        client.setRiskProfile(Risk.HIGH);

        clientService.create(client);
        clientService.delete(1L);

        List<ClientDto> clients = clientService.getAll();
        Assert.assertTrue(clients.isEmpty());
    }

    @Test(expected = NoClientExistsException.class)
    public void testDeleteNoSuchClientExpected() {
        clientService.delete(1L);

        List<ClientDto> clients = clientService.getAll();
        Assert.assertTrue(clients.isEmpty());
    }


    @Test
    public void testMerge() {
        ClientDto client = new ClientDto();
        client.setRiskProfile(Risk.HIGH);

        ClientDto client1 = new ClientDto();
        client1.setRiskProfile(Risk.LOW);

        ClientDto client2 = new ClientDto();
        client2.setRiskProfile(Risk.NORMAL);

        clientService.create(client);
        clientService.create(client1);
        clientService.create(client2);

        Set<Long> ids = new HashSet<>();
        ids.add(1L);
        ids.add(2L);
        ids.add(3L);

        List<ClientDto> clients = clientService.merge(ids);

        Assert.assertEquals(2, clients.size());
        Assert.assertEquals(Risk.HIGH, clients.get(0).getRiskProfile());
        Assert.assertEquals(Risk.HIGH, clients.get(1).getRiskProfile());
    }

}

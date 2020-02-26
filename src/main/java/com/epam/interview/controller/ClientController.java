package com.epam.interview.controller;

import com.epam.interview.dto.ClientDto;
import com.epam.interview.dto.IdSetWrapper;
import com.epam.interview.service.ClientService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("all")
    private List<ClientDto> getAll() { return clientService.getAll(); }

    @GetMapping("{id}")
    private ClientDto getById(@PathVariable @NotNull Long id) {
        return clientService.get(id);
    }

    @PostMapping
    private ClientDto create(@RequestBody ClientDto client) {
        return clientService.create(client);
    }

    @PutMapping
    private ClientDto update(@RequestBody @Valid ClientDto client) {
        return clientService.update(client);
    }

    @PutMapping("max-risk")
    private List<ClientDto> merge(@RequestBody @Valid IdSetWrapper wrapper) {
        return clientService.merge(wrapper.getIds());
    }

    @DeleteMapping("{id}")
    private void delete(@PathVariable @NotNull Long id) { clientService.delete(id); }

}

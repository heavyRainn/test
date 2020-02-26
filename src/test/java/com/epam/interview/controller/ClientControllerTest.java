package com.epam.interview.controller;

import com.epam.interview.dto.ClientDto;
import com.epam.interview.dto.IdSetWrapper;
import com.epam.interview.model.Risk;
import com.epam.interview.service.impl.ClientServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientServiceImpl clientService;

    @Test
    public void testMergeInputSizeValidation() throws Exception {
        ClientDto client = new ClientDto(1L, Risk.LOW);
        when(clientService.get(anyLong())).thenReturn(client);

        IdSetWrapper wrapper = new IdSetWrapper();
        Set<Long> ids = new HashSet<>();
        ids.add(1L);
        wrapper.setIds(ids);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(wrapper);

        mockMvc.perform(get("api/client/max-risk")
                .content(requestJson))
                .andExpect(status().is4xxClientError());
    }

}

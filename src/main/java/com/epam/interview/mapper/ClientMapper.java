package com.epam.interview.mapper;

import com.epam.interview.dto.ClientDto;
import com.epam.interview.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientDto toDto(Client client);

    @Mapping(target = "id", ignore = true)
    Client toEntityExcludeId(ClientDto client);

}

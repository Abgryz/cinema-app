package com.suitt.mapper;

import com.suitt.tables.client.Client;
import com.suitt.tables.client.ClientDto;

public class ClientMapper implements Mapper<ClientDto, Client>{
    @Override
    public ClientDto toDto(Client client) {
        return null;
    }

    @Override
    public Client toEntity(ClientDto clientDto) {
        return null;
    }
}

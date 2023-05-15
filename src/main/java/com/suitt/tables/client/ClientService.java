package com.suitt.tables.client;

import com.suitt.tables.ticketSales.TicketSales;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientService {
    private ClientRepository repository;

    public ClientDto getClient(String phoneNumber){
        return repository.findById(phoneNumber)
                .map(ClientService::mapClient)
                .orElse(null);
    }

    public List<ClientDto> getAll(){
        return repository.findAll().stream()
                .map(ClientService::mapClient)
                .collect(Collectors.toList());
    }

    private static ClientDto mapClient(Client client){
        return ClientDto.builder()
                .address(client.getAddress())
                .fullName(client.getFullName())
                .phoneNumber(client.getPhoneNumber())
                .birthDate(client.getBirthDate())
                .ticketSales(client.getTicketSales().stream()
                        .map(TicketSales::getTicketSalesPK)
                        .collect(Collectors.toList()))
                .build();
    }
}

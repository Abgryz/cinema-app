package com.suitt.tables.client;

import com.suitt.security.user.UserDto;
import com.suitt.security.user.UserEntity;
import com.suitt.security.user.UserService;
import com.suitt.tables.employee.Employee;
import com.suitt.tables.employee.EmployeeDto;
import com.suitt.tables.employee.EmployeeRepository;
import com.suitt.tables.film.Film;
import com.suitt.tables.film.FilmRepository;
import com.suitt.tables.jobTittle.JobTittleRepository;
import com.suitt.tables.ticketSales.TicketSales;
import com.suitt.tables.ticketSales.TicketSalesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final TicketSalesRepository ticketSalesRepository;
    private final PasswordEncoder passwordEncoder;

    public static UserDto toDto(Client client){
        return ClientDto.builder()
                .address(client.getAddress())
                .fullName(client.getFullName())
                .email(client.getEmail())
                .birthDate(client.getBirthDate())
                .ticketSales(client.getTicketSales().stream()
                        .map(ticketSales -> ticketSales
                                .getTicketSalesPK()
                                .getTicket()
                                .getId())
                        .collect(Collectors.toList()))
//                .role(client.getRole())
                .password(null)
                .active(client.isActive())
                .build();
    }

    public UserEntity toEntity(ClientDto clientDto){
        return Client.builder()
                .address(clientDto.address())
                .email(clientDto.email())
                .birthDate(clientDto.birthDate())
                .fullName(clientDto.fullName())
                .ticketSales(ticketSalesRepository.findByEmployee(clientDto.email()))
//                .role(client.role())
                .active(clientDto.active())
                .password(passwordEncoder.encode(clientDto.password()))
                .build();
    }
}

package com.suitt.tables.client;

import com.suitt.security.user.Role;
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
                .role(Role.USER.name())
                .password(client.getPassword())
                .active(client.isActive())
                .build();
    }

    public Client toEntity(UserDto userDto){
        return Client.builder()
                .address(userDto.getAddress())
                .email(userDto.getEmail())
                .birthDate(userDto.getBirthDate())
                .fullName(userDto.getFullName())
                .ticketSales(ticketSalesRepository.findByEmployee(userDto.getEmail()))
//                .role(client.role())
                .active(userDto.isActive())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .build();
    }
}

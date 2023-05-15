package com.suitt.tables.employee;

import com.suitt.tables.film.Film;
import com.suitt.tables.ticketSales.TicketSales;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private EmployeeRepository repository;

    private EmployeeDto getEmployee(String phoneNumber){
        return repository.findById(phoneNumber)
                .map(EmployeeService::mapEmployee)
                .orElse(null);
    }

    private List<EmployeeDto> getAll(){
        return repository.findAll().stream()
                .map(EmployeeService::mapEmployee)
                .collect(Collectors.toList());
    }

    private static EmployeeDto mapEmployee(Employee employee){
        return EmployeeDto.builder()
                .address(employee.getAddress())
                .empDate(employee.getEmpDate())
                .fullName(employee.getFullName())
                .jobTittleName(employee.getJobTittle().getName())
                .phoneNumber(employee.getPhoneNumber())
                .birthDate(employee.getBirthDate())
                .films(employee.getFilms().stream()
                        .map(Film::getId)
                        .collect(Collectors.toList()))
                .ticketSales(employee.getTicketSales().stream()
                        .map(TicketSales::getTicketSalesPK)
                        .collect(Collectors.toList()))
                .build();
    }
}

package com.suitt.mapper;

import com.suitt.tables.employee.Employee;
import com.suitt.tables.employee.EmployeeDto;
import com.suitt.tables.film.Film;
import com.suitt.tables.jobTittle.JobTittleRepository;
import com.suitt.tables.ticketSales.TicketSales;

import java.util.stream.Collectors;

public class EmployeeMapper implements Mapper<EmployeeDto, Employee>{
    @Override
    public EmployeeDto toDto(Employee employee) {

    }

    @Override
    public Employee toEntity(EmployeeDto employeeDto) {
        return Employee.builder()
                .address(employeeDto.address())
                .empDate(employeeDto.empDate())
                .fullName(employeeDto.fullName())
                .jobTittle(.employeeDto.jobTittleName())
                .phoneNumber(employeeDto.getPhoneNumber())
                .birthDate(employeeDto.getBirthDate())
                .films(employeeDto.getFilms().stream()
                        .map(Film::getId)
                        .collect(Collectors.toList()))
                .ticketSales(employeeDto.getTicketSales().stream()
                        .map(TicketSales::getTicketSalesPK)
                        .collect(Collectors.toList()))
                .role(employeeDto.getRole())
                .active(employeeDto.isActive())
                .password(null)
                .build();
    }
}

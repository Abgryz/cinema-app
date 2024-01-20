package com.suitt.tables.employee;

import com.suitt.security.user.Role;
import com.suitt.security.user.UserDto;
import com.suitt.security.user.UserEntity;
import com.suitt.security.user.UserService;
import com.suitt.tables.cinemaShow.CinemaShowRepository;
import com.suitt.tables.cinemaShow.CinemaShowService;
import com.suitt.tables.client.ClientRepository;
import com.suitt.tables.film.Film;
import com.suitt.tables.film.FilmRepository;
import com.suitt.tables.jobTittle.JobTittle;
import com.suitt.tables.jobTittle.JobTittleRepository;
import com.suitt.tables.ticketSales.TicketSales;
import com.suitt.tables.ticketSales.TicketSalesRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final JobTittleRepository jobTittleRepository;
    private final TicketSalesRepository ticketSalesRepository;
    private final FilmRepository filmRepository;
    private final PasswordEncoder passwordEncoder;

    public static UserDto toDto(Employee employee){
        return EmployeeDto.builder()
                .address(employee.getAddress())
                .empDate(employee.getEmpDate())
                .fullName(employee.getFullName())
                .jobTittleName(employee.getJobTittle()
                        .getName())
                .email(employee.getEmail())
                .birthDate(employee.getBirthDate())
                .films(employee.getFilms().stream()
                        .map(Film::getId)
                        .collect(Collectors.toList()))
                .ticketSales(employee.getTicketSales().stream()
                        .map(ticketSales -> ticketSales.getTicketSalesPK()
                                .getTicket()
                                .getId())
                        .collect(Collectors.toList()))
                .role(Role.getByJobTittle(employee.getJobTittle()))
                .password(employee.getPassword())
                .active(employee.isActive())
                .build();
    }

    public UserEntity toEntity(EmployeeDto employeeDto){
        return Employee.builder()
                .address(employeeDto.getAddress())
                .empDate(employeeDto.getEmpDate())
                .email(employeeDto.getEmail())
                .birthDate(employeeDto.getBirthDate())
                .fullName(employeeDto.getFullName())
                .films(filmRepository.findByEmployee(employeeDto.getEmail()))
                .ticketSales(ticketSalesRepository.findByEmployee(employeeDto.getEmail()))
                .jobTittle(jobTittleRepository.findById(employeeDto.getJobTittleName()).orElse(null))
//                .role(employeeDto.role())
                .active(employeeDto.isActive())
                .password(passwordEncoder.encode(employeeDto.getPassword()))
                .build();
    }
}

package com.suitt.tables.employee;

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
//                .role(employee.getRole())
                .password(null)
                .active(employee.isActive())
                .build();
    }

    public UserEntity toEntity(EmployeeDto employeeDto){
        return Employee.builder()
                .address(employeeDto.address())
                .empDate(employeeDto.empDate())
                .email(employeeDto.email())
                .birthDate(employeeDto.birthDate())
                .fullName(employeeDto.fullName())
                .films(filmRepository.findByEmployee(employeeDto.email()))
                .ticketSales(ticketSalesRepository.findByEmployee(employeeDto.email()))
                .jobTittle(jobTittleRepository.findById(employeeDto.jobTittleName()).orElse(null))
//                .role(employeeDto.role())
                .active(employeeDto.active())
                .password(passwordEncoder.encode(employeeDto.password()))
                .build();
    }
}

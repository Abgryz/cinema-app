package com.suitt.security.user;

import com.suitt.tables.client.Client;
import com.suitt.tables.client.ClientDto;
import com.suitt.tables.client.ClientRepository;
import com.suitt.tables.client.ClientService;
import com.suitt.tables.employee.EmployeeDto;
import com.suitt.tables.employee.EmployeeRepository;
import com.suitt.tables.employee.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    protected final ClientRepository clientRepository;
    protected final EmployeeRepository employeeRepository;
    private final ClientService clientService;
    private final EmployeeService employeeService;

    public Optional<UserDto> getUser(String email){
        return Optional.ofNullable(employeeRepository.findById(email)
                .map(EmployeeService::toDto)
                .orElse(
                        clientRepository.findById(email)
                                .map(ClientService::toDto)
                                .orElse(null)
                ));
    }

    public void registerClient(UserDto user){
        if (clientRepository.findById(user.email).isPresent()){
            throw new IllegalArgumentException("User already exist");
        }
        Client client = clientService.toEntity(user);
        clientRepository.save(client);
//        return true;
    }


    public List<UserDto> getEmployees(){
        return employeeRepository.findAll().stream()
                .map(EmployeeService::toDto)
                .collect(Collectors.toList());
    }

    public List<UserDto> getClients(){
        return clientRepository.findAll().stream()
                .map(ClientService::toDto)
                .collect(Collectors.toList());
    }




    public EmployeeDto getEmployee(String email){
        return (EmployeeDto) employeeRepository.findById(email)
                .map(EmployeeService::toDto)
                .orElse(null);
    }
    public ClientDto getClient(String email){
        return (ClientDto) clientRepository.findById(email)
                .map(ClientService::toDto)
                .orElse(null);
    }

    public static Authentication authentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}

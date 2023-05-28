package com.suitt.security.user;

import com.suitt.tables.client.Client;
import com.suitt.tables.client.ClientDto;
import com.suitt.tables.client.ClientRepository;
import com.suitt.tables.client.ClientService;
import com.suitt.tables.employee.Employee;
import com.suitt.tables.employee.EmployeeDto;
import com.suitt.tables.employee.EmployeeRepository;
import com.suitt.tables.employee.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    protected final ClientRepository clientRepository;
    protected final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;


    public Optional<UserDto> getUser(String email){
        return Optional.ofNullable(employeeRepository.findById(email)
                .map(EmployeeService::toDto)
                .orElse(
                        clientRepository.findById(email)
                                .map(ClientService::toDto)
                                .orElse(null)
                ));
    }

    public void registerClient(String email, String password){
        if (clientRepository.existsById(email)){
            throw new IllegalArgumentException("User already exist");
        }
        Client client = Client.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .active(true)
                .build();
        clientRepository.save(client);
    }

    public void updateUser(UserDto userDto){
        if (userDto.role.equals(Role.ROLE_USER.name())){
            Client client = clientRepository.findById(userDto.email).orElseThrow();
            clientRepository.save(
                    client.toBuilder()
                            .address(userDto.address)
                            .birthDate(userDto.birthDate)
                            .fullName(userDto.fullName)
                            .build()
            );
        } else {
            Employee employee = employeeRepository.findById(userDto.email).orElseThrow();
            employeeRepository.save(
                    employee.toBuilder()
                            .address(userDto.address)
                            .birthDate(userDto.birthDate)
                            .fullName(userDto.fullName)
                            .build()
            );
        }
    }

    public static List<String> getRoles(Authentication authentication){
        List<String> roles = new ArrayList<>();
        try {
            List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();
            for (var grantedAuthority : authorities) {
                roles.add(grantedAuthority.getAuthority());
            }
        } catch (RuntimeException e){
            return roles;
        }
        return roles;
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
}

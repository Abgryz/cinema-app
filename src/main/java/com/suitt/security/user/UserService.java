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
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

        if (clientRepository.existsById(user.email)){
            throw new IllegalArgumentException("User already exist");
        }
//        Client client = clientService.toEntity(user);
        clientRepository.register(user.email, user.password);
//        return true;
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
//    public Map<String, String> getAddressAsMap(String address){
//        Map<String, String> addressMap = new HashMap<>();
//        String[] addressArr = address.substring(1, address.length() - 1).split(",", 4);
//
//        addressMap.put("city", addressArr[0]);
//        addressMap.put("street", addressArr[1]);
//        addressMap.put("house", addressArr[2]);
//        addressMap.put("float", addressArr[3]);
//
//        System.out.println(address);
//        return addressMap;
//    }
}

package com.suitt.tables.jobTittle;

import com.suitt.tables.employee.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobTittleService {
    private final JobTittleRepository repository;

    public JobTittleDto getJobTittle(String name){
        return repository.findById(name)
                .map(JobTittleService::mapJobTittle)
                .orElse(null);
    }

    public List<JobTittleDto> getAll(){
        return repository.findAll().stream()
                .map(JobTittleService::mapJobTittle)
                .collect(Collectors.toList());
    }

    private static JobTittleDto mapJobTittle(JobTittle jobTittle){
        return JobTittleDto.builder()
                .name(jobTittle.getName())
                .salary(jobTittle.getSalary())
                .employees(jobTittle.getEmployees().stream()
                        .map(Employee::getEmail)
                        .collect(Collectors.toList()))
                .build();
    }
}

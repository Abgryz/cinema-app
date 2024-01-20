package com.suitt.tables.jobTittle;

import com.suitt.tables.employee.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Table(name = "job_tittle")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobTittle {
    @Id
    @GeneratedValue
    @Column(name = "job_tittle_name")
    private String name;

    @Column(name = "salary")
    private int salary;

    @OneToMany(mappedBy = "jobTittle", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Employee> employees;
}

package com.suitt.tables.jobTittle;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


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
}

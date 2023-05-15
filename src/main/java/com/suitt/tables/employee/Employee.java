package com.suitt.tables.employee;

import com.suitt.tables.film.Film;
import com.suitt.tables.jobTittle.JobTittle;
import com.suitt.tables.ticketSales.TicketSales;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "employee")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {
    @Id
    @Column(name = "emp_phone_number")
    private String phoneNumber;

    @Column(name = "emp_full_name")
    private String fullName;

    private String address;

    private LocalDate birthDate;

    private LocalDate empDate;

    @ManyToOne
    @JoinColumn(name = "job_tittle_name")
    private JobTittle jobTittle;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Film> films;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TicketSales> ticketSales;
}

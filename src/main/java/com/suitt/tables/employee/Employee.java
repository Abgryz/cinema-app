package com.suitt.tables.employee;

import com.suitt.security.user.UserEntity;
import com.suitt.tables.film.Film;
import com.suitt.tables.jobTittle.JobTittle;
import com.suitt.tables.ticketSales.TicketSales;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "employee")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee extends UserEntity {
    @Id
    @Column(name = "emp_email")
    private String email;

    @Column(name = "emp_full_name")
    private String fullName;

    private String address;

    private LocalDate birthDate;

    private LocalDate empDate;

    private String password;

    private boolean active;
//
//    private String role;

    @ManyToOne
    @JoinColumn(name = "job_tittle_name")
    private JobTittle jobTittle;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Film> films;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TicketSales> ticketSales;
}

package com.suitt.tables.employee;

import com.suitt.security.user.UserEntity;
import com.suitt.tables.film.Film;
import com.suitt.tables.jobTittle.JobTittle;
import com.suitt.tables.ticketSales.TicketSales;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "employee")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Employee extends UserEntity {

    private LocalDate empDate;

    @ManyToOne
    @JoinColumn(name = "job_tittle_name")
    private JobTittle jobTittle;

    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Film> films;

    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<TicketSales> ticketSales;
}

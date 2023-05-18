package com.suitt.controllers;

import com.suitt.security.user.UserDto;
import com.suitt.security.user.UserService;
import com.suitt.tables.cinemaShow.CinemaShowService;
import com.suitt.tables.film.FilmService;
import com.suitt.tables.hall.HallService;
import com.suitt.tables.jobTittle.JobTittleDto;
import com.suitt.tables.jobTittle.JobTittleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TestRestController {
    private final FilmService filmService;
    private final HallService hallService;
    private final CinemaShowService cinemaShowService;
    private final UserService userService;
    private final JobTittleService jobTittleService;

    @GetMapping("/users")
    public List<UserDto> getUsers(){
        var users = userService.getEmployees();
        users.addAll(userService.getClients());
        return users;
    }

    @GetMapping("/jobs")
    public List<JobTittleDto> getJobs(){
        var jobs = jobTittleService.getAll();
        return jobs;
    }

    @GetMapping("/users/{id}")
    public UserDto getUsers(@PathVariable("id")String id){
        var user = userService.getEmployee(id);
//        users.addAll(userService.getEmployees());
        return user;
    }
}

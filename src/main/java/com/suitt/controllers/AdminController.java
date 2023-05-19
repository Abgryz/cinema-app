package com.suitt.controllers;

import com.suitt.tables.genre.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admins")
public class AdminController {
    @GetMapping("/films")
    public String films(Model model){
        return "film-adding";
    }

    @GetMapping("/cinemashows")
    public String cinemaShows(Model model){
        return "cinemashow-adding";
    }

}

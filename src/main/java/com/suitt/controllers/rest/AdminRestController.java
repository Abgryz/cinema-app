package com.suitt.controllers.rest;

import com.suitt.tables.genre.GenreDto;
import com.suitt.tables.genre.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins")
public class AdminRestController {
    private final GenreService genreService;

    @GetMapping("/films")
    public List<GenreDto> films(){
        return genreService.getAll();
    }

}

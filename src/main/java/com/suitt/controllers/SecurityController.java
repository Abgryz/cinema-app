package com.suitt.controllers;

import com.suitt.models.Response;
import com.suitt.security.user.UserDto;
import com.suitt.security.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SecurityController {
    private final UserService userService;

    @PostMapping("/register")
    Response register(@RequestBody UserDto user) {
        userService.registerClient(user);
        return Response.ok(null);
    }

//    @GetMapping("/users/profile")
//    public String userProfile(Model model, Authentication authentication) {
//        model.addAttribute("username", authentication.getName());
//        return "profile";
//    }
}

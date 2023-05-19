package com.suitt.controllers;

import com.suitt.models.Response;
import com.suitt.security.user.UserDto;
import com.suitt.security.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SecurityController {
    private final UserService userService;
    @PostMapping("/register")
    public Response handleFormSubmit(@RequestParam("username") String username,
                                   @RequestParam("password") String password,
                                   @RequestParam("repeat-password") String repeatPassword,
                                   Model model){

        System.out.println("ITS WORKING");
        if (password.equals(repeatPassword)) {
            UserDto user = UserDto.builder()
                    .email(username)
                    .password(password).build();
            userService.registerClient(user);
            log.info("User registered: " + user);
//            return "login";
            return Response.ok(null);
        } else {
            model.addAttribute("error", "Паролі не співпадають");
            return Response.fail();
        }
    }
}




//    @PostMapping("/register")
//    Response register(@RequestBody UserDto user) {
//        System.out.println(123);
//        userService.registerClient(user);
//        return Response.ok(null);
//    }

//    @GetMapping("/users/profile")
//    public String userProfile(Model model, Authentication authentication) {
//        model.addAttribute("username", authentication.getName());
//        return "profile";
//    }

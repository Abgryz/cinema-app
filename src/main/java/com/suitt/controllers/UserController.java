package com.suitt.controllers;

import com.suitt.security.user.UserDto;
import com.suitt.security.user.UserService;
import com.suitt.tables.ticket.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final TicketService ticketService;

    @GetMapping("/profile-loader")
    public String redirectProfile(Authentication authentication){
        StringBuilder param = new StringBuilder("?");
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();
        for (var grantedAuthority : authorities){
            param.append(grantedAuthority.getAuthority().substring(5).toLowerCase()).append("&");
        }

        return "redirect:/profile" + param;
    }

    @GetMapping("/profile")
    public String profile(Model model){
        UserDto userDto = userService.getUser(UserService.authentication().getName()).orElseThrow();
        model.addAttribute("user", userDto);
        model.addAttribute("ticketsData", ticketService.getTicketBookingDataByClient(UserService.authentication().getName()));
        return "profile";
    }


    @GetMapping("/login")
    public String login(Model model){
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model){
        return "register";
    }
}

package com.suitt.controllers.rest;

import com.suitt.models.Response;
import com.suitt.security.user.UserDto;
import com.suitt.security.user.UserService;
import com.suitt.tables.cinemaShow.CinemaShowDto;
import com.suitt.tables.cinemaShow.CinemaShowService;
import com.suitt.tables.ticket.TicketService;
import com.suitt.tables.ticketSales.TicketSalesDto;
import com.suitt.tables.ticketSales.TicketSalesService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserRestController {
    private final CinemaShowService cinemaShowService;
    private final TicketSalesService ticketSalesService;
    private final TicketService ticketService;
    private final UserService userService;

    @PostMapping("/schedule/{cinemaShowId}")
    @Transactional
    public Response ticketBooking(@PathVariable Long cinemaShowId, @RequestParam("seatId") Long seatId){
        TicketSalesDto ticketSalesDto = TicketSalesDto.builder()
                .isBooking(true)
                .client(UserService.authentication().getName())
                .ticket(ticketService.findByCinemaShowAndSeat(cinemaShowId, seatId).id())
                .build();
        ticketSalesService.save(ticketSalesDto);
        return Response.ok(null);
    }

    @PostMapping("/profile")
    @Transactional
    public Response updateProfile(@RequestParam(value = "fullName", required = false) String fullName,
                                  @RequestParam(value = "birthDate", required = false) LocalDate birthDate,
                                  @RequestParam(value = "address", required = false) String address){
        UserDto userDto = userService.getUser(UserService.authentication().getName()).orElseThrow();
        userService.updateUser(
                userDto.toBuilder()
                        .birthDate(birthDate == null ? userDto.getBirthDate() : birthDate)
                        .fullName(fullName == null ? userDto.getFullName() : fullName)
                        .address(address == null ? userDto.getAddress() : address)
                        .build()
        );
        return Response.ok(null);
    }

    @DeleteMapping("/cancel-booking/{ticketId}")
    @Transactional
    public Response cancelBooking(@PathVariable Long ticketId){
        UserDto userDto = userService.getUser(UserService.authentication().getName()).orElseThrow();
        TicketSalesDto ticketSalesDto = ticketSalesService.getTicketSales(ticketId);
        CinemaShowDto cinemaShowDto = cinemaShowService.getByTicket(ticketId);

        if (!ticketSalesDto.client().equals(userDto.getEmail()) ||
                !ticketSalesDto.isBooking() ||
                cinemaShowDto.dateAndTime().isBefore(LocalDateTime.now())
        ){
            System.out.println("Ticket booking didn`t delete");
            return Response.fail();
        } else {
            ticketSalesService.delete(ticketId);
            System.out.println("Ticket booking deleted " + ticketId);
            return Response.ok(null);
        }
    }

    @PostMapping("/register")
    @Transactional
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

package com.suitt.controllers.rest;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
    public ResponseEntity<String> ticketBooking(@PathVariable Long cinemaShowId, @RequestParam("seatId") Long seatId, Authentication authentication){
        TicketSalesDto ticketSalesDto = TicketSalesDto.builder()
                .isBooking(true)
                .client(authentication.getName())
                .ticket(ticketService.findByCinemaShowAndSeat(cinemaShowId, seatId).id())
                .build();
        ticketSalesService.save(ticketSalesDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/profile")
    @Transactional
    public ResponseEntity<String> updateProfile(@RequestParam(value = "fullName", required = false) String fullName,
                                  @RequestParam(value = "birthDate", required = false) LocalDate birthDate,
                                  @RequestParam(value = "address", required = false) String address,
                                  Authentication authentication){
        UserDto userDto = userService.getUser(authentication.getName()).orElseThrow();
        userService.updateUser(
                userDto.toBuilder()
                        .birthDate(birthDate == null ? userDto.getBirthDate() : birthDate)
                        .fullName(fullName == null ? userDto.getFullName() : fullName)
                        .address(address == null ? userDto.getAddress() : address)
                        .build()
        );
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/ticket-sales/{ticketId}")
    @Transactional
    public ResponseEntity<String> cancelBooking(@PathVariable Long ticketId, Authentication authentication){
        UserDto userDto = userService.getUser(authentication.getName()).orElseThrow();
        TicketSalesDto ticketSalesDto = ticketSalesService.getTicketSales(ticketId);
        CinemaShowDto cinemaShowDto = cinemaShowService.getByTicket(ticketId);

        if (!ticketSalesDto.client().equals(userDto.getEmail()) ||
                !ticketSalesDto.isBooking() ||
                cinemaShowDto.dateAndTime().isBefore(LocalDateTime.now())
        ){
            System.out.println("Ticket booking didn`t delete");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ticket booking didn`t delete");
        } else {
            ticketSalesService.delete(ticketId);
            System.out.println("Ticket booking deleted " + ticketId);
            return ResponseEntity.ok().build();
        }
    }

    @GetMapping("/schedule/{id}")
    public List<?> tickets(@PathVariable Long id){
        return ticketSalesService.ticketsToMap(ticketService.getByCinemaShow(id));
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<String> register(
            @RequestParam("username") String username,
            @RequestParam("password") String password) {
        try {
            userService.registerClient(username, password);
            log.info("User registered: " + username);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}

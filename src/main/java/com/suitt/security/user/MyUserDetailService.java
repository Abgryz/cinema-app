//package com.suitt.security.user;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j;
//import org.springframework.security.authentication.DisabledException;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.Collections;
//
//@Service
//@Log4j
//@RequiredArgsConstructor
//public class MyUserDetailService implements UserDetailsService {
//
//    private final UserService userService;
//    private final PasswordEncoder passwordEncoder;
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        log.info("Load user by username: " + username);
//
//        return userService.findUser(username)
//                .map(user -> {
//                    if (!user.getEnabled()) {
//                        throw new DisabledException("User account is disabled");
//                    }
//                    return new User(
//                            user.getEmail(),
//                            passwordEncoder.encode(user.getHashpassword()),
//                            Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))
//                    );
//                })
//                .orElseThrow(() -> new UsernameNotFoundException("User" + username + " not found"));
//    }
//
//

package com.suitt.security.config;

import com.suitt.security.user.Role;
import com.suitt.security.user.details.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final String USER = Role.ROLE_USER.name();
    private final String MANAGER = Role.ROLE_MANAGER.name();
    private final String CASHIER = Role.ROLE_CASHIER.name();

    private final static String[] STATIC_RESOURCES =  {
            "/css/**",
            "/images/**",
            "/fonts/**",
            "/scripts/**",
            "/files/**",
            "favicon.ico"
    };
    private final static String[] HTML_RESOURCES ={
            "/",
            "/films/**",
            "/register",
            "/schedule",
            "/search",
            "/login",
            "/fragments/header"
    };
    private final static String[] USER_RESOURCES = {
            "/profile",
            "/profile-loader",
            "/schedule/**",
            "/api/profile",
            "/api/schedule/**"
    };
    private final static String[] MANAGER_RESOURCES = {
            "/admins/films/**",
            "/admins/shows/**",
            "/api/admins/films/**",
            "/api/admins/shows/**",
            "/api/admins/genres"
    };
    private final static String[] CASHIER_RESOURCES = {
            "/admins/ticket-sales/**",
            "/api/admins/ticket-sales/**"
    };

    private final static String[] REST_RESOURCES = {
            "/api/",
            "/api/films/**",
            "/api/register",
            "/api/schedule/**",
            "/api/ticket-sales/**",
            "/api/shows/**",
            "/api/genres/**",
            "/api/halls/**"
    };
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/**").permitAll()
                        .requestMatchers(STATIC_RESOURCES).permitAll()
                        .requestMatchers(HTML_RESOURCES).permitAll()
                        .requestMatchers(REST_RESOURCES).permitAll()
                        .requestMatchers(USER_RESOURCES).hasAnyAuthority(USER, MANAGER, CASHIER)
                        .requestMatchers(MANAGER_RESOURCES).hasAuthority(MANAGER)
                        .requestMatchers(CASHIER_RESOURCES).hasAuthority(CASHIER)
//                        .anyRequest().authenticated()
                )
                .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/login")
                    .defaultSuccessUrl("/")
                    .failureUrl("/login?error")
                    .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout")
                    .permitAll()
                    .and()
                .userDetailsService(userDetailsService)
                    .csrf().disable();
        return http.build();
    }
}
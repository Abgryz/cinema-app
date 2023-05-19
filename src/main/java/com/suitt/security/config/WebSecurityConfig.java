package com.suitt.security.config;

import com.suitt.security.user.Role;
import com.suitt.security.user.details.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService);
//    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        String[] staticResources  =  {
                "/css/**",
                "/images/**",
                "/fonts/**",
                "/scripts/**",
                "/files/**"
        };
        String[] htmlResources ={
                "/",
                "/films/**",
                "/register",
                "/schedule",
                "/search",
                "/login"
        };
        String[] restResources = {
                "/api/",
                "/api/films/**",
                "/api/register",
                "/api/schedule"
        };

        String[] managerResources = {
                "/api/admins/films",
                "/api/admins/cinemashows"
        };
        String[] cashierResources = {
                "/api/admins/tickets"
        };
        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers( "/register", "/api/register", "/registration/users/register", "/users/register").permitAll()
                        .requestMatchers(staticResources).permitAll()
                        .requestMatchers(htmlResources).permitAll()
                        .requestMatchers(restResources).permitAll()
//                        .requestMatchers("/**").permitAll()
                        .requestMatchers("/fragments/header").permitAll()

                        .requestMatchers("/profile", "/api/profile").hasAnyAuthority(USER, MANAGER, CASHIER)
                        .requestMatchers("/admins/**").hasAnyAuthority(MANAGER, CASHIER)
                        .requestMatchers(managerResources).hasAuthority(MANAGER)
                        .requestMatchers(cashierResources).hasAuthority(CASHIER)
                        .anyRequest().authenticated())
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
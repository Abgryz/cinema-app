//package com.suitt.security.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//
//
//import lombok.RequiredArgsConstructor;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
//
////    private final MyUserDetailService userDetailsService;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        String[] staticResources  =  {
//                "/css/**",
//                "/images/**",
//                "/fonts/**",
//                "/scripts/**",
//        };
//
//        String[] htmlResources ={
//                "/index","index.html",
//                "/preAuthStartPage","preAuthStartPage.html",
//                "/registration","registration.html",
//                "/login","login.html",
//                "/afterAuthStartPage","afterAuthStartPage.html",
//
//        };
//
//        String[] afterAuthHtmlResources ={
//                "/afterAuthStartPage","afterAuthStartPage.html",
//                "/profile/**","profile.html","/afterAuthStartPage/**"
//        };
//
//        http.authorizeHttpRequests(authorize -> authorize.requestMatchers("/").permitAll()
//                        .requestMatchers(staticResources).permitAll()
//                        .requestMatchers(htmlResources).permitAll()
//                        .requestMatchers("/registration/users/register","/users/register","/register").permitAll()
//                        .requestMatchers("/admins/**").hasAuthority("ROLE_ADMIN")
//                        .requestMatchers("/users/**").hasAuthority("ROLE_USER")
//                        .requestMatchers(afterAuthHtmlResources).hasAuthority("ROLE_USER")
//                        .requestMatchers(afterAuthHtmlResources).hasAuthority("ROLE_ADMIN")
//                        .anyRequest().authenticated())
//                .formLogin()
//                .loginPage("/login.html")
//                .loginProcessingUrl("/login")
//                .defaultSuccessUrl("/users/afterAuthStartPage.html",true)
//                .failureUrl("/login.html")
//                .and()
//                .userDetailsService(userDetailsService)
//                .csrf().disable();
//        return http.build();
//    }
//
//
//}

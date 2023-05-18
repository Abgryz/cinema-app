package com.suitt.security.config;

import com.suitt.security.user.detail.UserDetailsServiceImpl;
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


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
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
                "/index","index.html",
                "/startPage","startPage.html",
                "/registration","registration.html",
                "/login","login.html",
                "/afterAuthStartPage","afterAuthStartPage.html",
                "/profilePage/**","profilePage.html",

        };

        String[] afterAuthHtmlResources ={

        };

        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/").permitAll()
                        .requestMatchers(staticResources).permitAll()
                        .requestMatchers(htmlResources).permitAll()
                        .requestMatchers("/**").permitAll()
                        .requestMatchers("/registration/users/register","/users/register","/register").permitAll()
                        .requestMatchers("/fragments/header").permitAll()
                        .requestMatchers("/admins/**").hasAnyAuthority("MANAGER", "CASHIER")
                        .requestMatchers(afterAuthHtmlResources).hasAnyAuthority("USER", "MANAGER", "CASHIER")
                        .anyRequest().authenticated())
                .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/login")
                    .defaultSuccessUrl("/home",true)
                    .failureUrl("/login?error")
                    .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout")
                    .permitAll();
//                    .and()
//                .userDetailsService(userDetailsService)
//                    .csrf().disable();
        return http.build();
    }


//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user =
//                User.withDefaultPasswordEncoder()
//                        .username("user")
//                        .password("user")
//                        .roles("USER")
//                        .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }
//
//    @Bean
//    protected DaoAuthenticationProvider daoAuthenticationProvider(){
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
//        daoAuthenticationProvider.setUserDetailsService(userDetailService);
//        return daoAuthenticationProvider;
//    }
//
//    protected void configure(AuthenticationManagerBuilder auth){
//        auth.authenticationProvider(daoAuthenticationProvider());
//    }
}
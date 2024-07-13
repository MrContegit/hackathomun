package com.conte.hackothumun.config.security;

import com.conte.hackothumun.entity.UserApp;
import com.conte.hackothumun.repository.UserAppRepo;
import com.conte.hackothumun.service.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Autowired
    private final PasswordEncoder passwordEncoder;
    private final AccountServiceImpl accountService;

    public SecurityConfig(PasswordEncoder passwordEncoder, AccountServiceImpl accountService) {
        this.passwordEncoder = passwordEncoder;
        this.accountService = accountService;
    }


//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(accountService).passwordEncoder(passwordEncoder);
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedPage("/notauthorized")
                )
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/webjars/**").permitAll()
                        .requestMatchers("/index").permitAll()
                        .requestMatchers("/home").permitAll()
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/createUser").permitAll()
                        .requestMatchers("/oneEvent").permitAll()
                        .requestMatchers("/createEvent").permitAll()
                        .requestMatchers("/showUser").permitAll()
                        .requestMatchers("/formEvents").permitAll()
                        .requestMatchers("/public/**").permitAll()
                        .requestMatchers("/uploads/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasRole("USER")
                        .anyRequest().authenticated()
                )
                .formLogin( form -> form
                                .loginPage("/login")
                                .defaultSuccessUrl("/index", true)
                        .failureUrl("/login?error=true")
                                .permitAll()

                )
                .rememberMe( re ->re.tokenValiditySeconds(8500))
        ;
        return httpSecurity.build();
    }

}

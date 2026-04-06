package com.company.HospitalManagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Modern way to disable CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/main", "/signup", "/login*", "/anonymous*").permitAll()
                        .requestMatchers("/doctors/**").hasRole("DOCTOR")
                        .requestMatchers("/patients/**").hasRole("PATIENT")
                        .requestMatchers("/receptionist/**").hasRole("RECEPTIONIST")
//                        .requestMatchers("/doctors/**", "/calendar/**", "/jsoncalendar/**").hasRole("DOCTOR")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .defaultSuccessUrl("/showPostLogin", false)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        List<UserDetails> users = new ArrayList<>();

        // Helper to add users to our in-memory list

        // Existing Users
        users.add(createUser(encoder, "marry", "test123", "USER", "RECEPTIONIST"));
        users.add(createUser(encoder, "bijoy", "test123", "USER", "DOCTOR", "RECEPTIONIST"));
        users.add(createUser(encoder, "damon", "test123", "USER", "DOCTOR", "RECEPTIONIST"));

// --- ADDING THE NEW DOCTORS FROM YOUR SCHEDULE ---
        users.add(createUser(encoder, "ananya", "test123", "USER", "DOCTOR", "RECEPTIONIST"));
        users.add(createUser(encoder, "raj", "test123", "USER", "DOCTOR", "RECEPTIONIST"));
        users.add(createUser(encoder, "meera", "test123", "USER", "DOCTOR", "RECEPTIONIST"));
        users.add(createUser(encoder, "arjun", "test123", "USER", "DOCTOR", "RECEPTIONIST"));
        users.add(createUser(encoder, "house", "test123", "USER", "DOCTOR", "RECEPTIONIST"));

// Existing Patients
        users.add(createUser(encoder, "walter", "test123", "USER", "PATIENT"));
        users.add(createUser(encoder, "varshaa", "test123", "USER", "PATIENT"));
        users.add(createUser(encoder, "pradeep", "test123", "USER", "PATIENT"));
        users.add(createUser(encoder, "rupa", "test123", "USER", "PATIENT"));
        return new InMemoryUserDetailsManager(users);
    }

    private UserDetails createUser(PasswordEncoder encoder, String username, String password, String... roles) {
        return User.withUsername(username)
                .password(encoder.encode(password))
                .roles(roles)
                .build();
    }
}
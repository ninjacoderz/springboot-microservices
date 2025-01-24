package com.springbootmicroservice.discovery_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(
            authorizeRequests -> authorizeRequests
            .anyRequest().authenticated()
        ).csrf(
            (csrf) -> csrf.disable()
        ).securityMatcher("/eureka/**")
        .httpBasic(
            Customizer.withDefaults()
        );
        return httpSecurity.build();
    }

}

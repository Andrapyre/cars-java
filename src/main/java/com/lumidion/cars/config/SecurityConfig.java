package com.lumidion.cars.config;

import com.lumidion.cars.components.AuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
// To set debug logging on, just change to: @EnableWebSecurity(debug = true)
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    AuthFilter authFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requests -> requests.requestMatchers("/customers")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .csrf(CsrfConfigurer::disable)
                .addFilterAfter(authFilter, BasicAuthenticationFilter.class);

        return http.build();
    }
}

package com.hsmoco.capex.capexbackend.config;

import com.hsmoco.capex.capexbackend.security.SimpleAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final SimpleAuthenticationFilter simpleAuthenticationFilter;

    public SecurityConfig(SimpleAuthenticationFilter simpleAuthenticationFilter) {
        this.simpleAuthenticationFilter = simpleAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //TODO Configure authentication provider to login with user/pass
        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(authorize ->
                        authorize.requestMatchers(
                                        HttpMethod.GET, "/api/users").permitAll()
                                .requestMatchers(
                                        HttpMethod.OPTIONS, "/**").permitAll()
                                .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(simpleAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


}

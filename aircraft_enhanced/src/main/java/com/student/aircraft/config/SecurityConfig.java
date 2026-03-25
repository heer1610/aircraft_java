package com.student.aircraft.config;

import com.student.aircraft.security.CustomUserDetailsService;
import com.student.aircraft.security.JwtAuthFilter;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(CustomUserDetailsService userDetailsService, JwtAuthFilter jwtAuthFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/login", "/register", "/", "/dashboard", "/aircraft", "/maintenance", "/airlines", "/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/airlines/**").hasAnyRole("ADMIN","TECHNICIAN","VIEWER")
                .requestMatchers("/api/airlines/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/aircraft/**").hasAnyRole("ADMIN","TECHNICIAN","VIEWER")
                .requestMatchers(HttpMethod.POST, "/api/aircraft/**").hasAnyRole("ADMIN","TECHNICIAN")
                .requestMatchers(HttpMethod.PUT, "/api/aircraft/**").hasAnyRole("ADMIN","TECHNICIAN")
                .requestMatchers(HttpMethod.DELETE, "/api/aircraft/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/maintenance/**").hasAnyRole("ADMIN","TECHNICIAN","VIEWER")
                .requestMatchers("/api/maintenance/**").hasAnyRole("ADMIN","TECHNICIAN")
                .requestMatchers("/api/roles/**").hasRole("ADMIN")
                .requestMatchers("/api/users/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .authenticationProvider(authProvider())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}

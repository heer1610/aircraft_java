package com.student.aircraft.service;

import com.student.aircraft.dto.*;
import com.student.aircraft.model.Role;
import com.student.aircraft.model.User;
import com.student.aircraft.repository.RoleRepository;
import com.student.aircraft.repository.UserRepository;
import com.student.aircraft.security.JwtUtil;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder, JwtUtil jwtUtil,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public AuthResponse register(RegisterRequest req) {
        if (userRepository.existsByUsername(req.getUsername()))
            throw new RuntimeException("Username already taken: " + req.getUsername());
        if (userRepository.existsByEmail(req.getEmail()))
            throw new RuntimeException("Email already registered: " + req.getEmail());

        Set<Role> roles = resolveRoles(req.getRoles());

        User user = new User();
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setFullName(req.getFullName());
        user.setEnabled(true);
        user.setRoles(roles);
        userRepository.save(user);

        String token = jwtUtil.generateToken(user);
        Set<String> roleNames = roles.stream().map(Role::getName).collect(Collectors.toSet());
        return new AuthResponse(token, user.getUsername(), user.getEmail(), roleNames, "Registration successful");
    }

    public AuthResponse login(LoginRequest req) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        UserDetails ud = (UserDetails) auth.getPrincipal();
        String token = jwtUtil.generateToken(ud);
        User user = userRepository.findByUsername(req.getUsername()).orElseThrow();
        Set<String> roleNames = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
        return new AuthResponse(token, user.getUsername(), user.getEmail(), roleNames, "Login successful");
    }

    private Set<Role> resolveRoles(String rolesStr) {
        if (rolesStr == null || rolesStr.isBlank())
            return Set.of(getOrCreate("ROLE_VIEWER", "Read-only access"));
        return Arrays.stream(rolesStr.split(","))
                .map(String::trim)
                .map(name -> getOrCreate(name, name))
                .collect(Collectors.toSet());
    }

    private Role getOrCreate(String name, String desc) {
        return roleRepository.findByName(name).orElseGet(() -> {
            Role r = new Role();
            r.setName(name);
            r.setDescription(desc);
            return roleRepository.save(r);
        });
    }
}

package com.student.aircraft.config;

import com.student.aircraft.model.Role;
import com.student.aircraft.model.User;
import com.student.aircraft.repository.RoleRepository;
import com.student.aircraft.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepository, UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        // Seed roles first and get managed (attached) instances
        Role adminRole  = seedRole("ROLE_ADMIN",      "Full access");
        Role techRole   = seedRole("ROLE_TECHNICIAN", "Can manage aircraft and maintenance");
        Role viewerRole = seedRole("ROLE_VIEWER",     "Read-only access");

        // Seed users using the managed role instances
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@aircraft.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setFullName("System Administrator");
            admin.setEnabled(true);
            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);
            admin.setRoles(roles);
            userRepository.save(admin);
            log.info("Default admin created → username: admin | password: admin123");
        }

        if (!userRepository.existsByUsername("tech1")) {
            User tech = new User();
            tech.setUsername("tech1");
            tech.setEmail("tech1@aircraft.com");
            tech.setPassword(passwordEncoder.encode("tech123"));
            tech.setFullName("John Technician");
            tech.setEnabled(true);
            Set<Role> roles = new HashSet<>();
            roles.add(techRole);
            tech.setRoles(roles);
            userRepository.save(tech);
            log.info("Technician created → username: tech1 | password: tech123");
        }

        if (!userRepository.existsByUsername("viewer1")) {
            User viewer = new User();
            viewer.setUsername("viewer1");
            viewer.setEmail("viewer1@aircraft.com");
            viewer.setPassword(passwordEncoder.encode("view123"));
            viewer.setFullName("Jane Viewer");
            viewer.setEnabled(true);
            Set<Role> roles = new HashSet<>();
            roles.add(viewerRole);
            viewer.setRoles(roles);
            userRepository.save(viewer);
            log.info("Viewer created → username: viewer1 | password: view123");
        }
    }

    private Role seedRole(String name, String description) {
        return roleRepository.findByName(name).orElseGet(() -> {
            Role r = new Role();
            r.setName(name);
            r.setDescription(description);
            return roleRepository.save(r);
        });
    }
}

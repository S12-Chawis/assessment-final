package com.projectmanager.infrastructure.config;

import com.projectmanager.domain.model.User;
import com.projectmanager.domain.port.out.UserRepositoryPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Profile("!test")
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepositoryPort userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        String username = "admin";

        // Verificación simple
        if (userRepository.findByUsername(username).isEmpty()) {
            User admin = new User();
            // Si tu constructor requiere UUID, usa UUID.randomUUID()
            // pero asegúrate de que el objeto sea nuevo para JPA
            admin.setId(UUID.randomUUID());
            admin.setUsername(username);
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("admin123"));

            userRepository.save(admin);
            System.out.println(">>>> USUARIO CREADO EXITOSAMENTE");
        } else {
            System.out.println(">>>> EL USUARIO YA EXISTÍA EN DB");
        }
    }
}
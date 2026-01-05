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
    @Transactional // Agrega esto para asegurar que la operación sea atómica
    public void run(String... args) {
        String defaultUsername = "admin";

        // Verificamos si ya existe para evitar conflictos de transacciones
        if (userRepository.findByUsername(defaultUsername).isEmpty()) {
            User admin = new User(
                    UUID.randomUUID(),
                    defaultUsername,
                    "admin@example.com",
                    passwordEncoder.encode("admin123")
            );
            try {
                userRepository.save(admin);
                System.out.println(">>>> USUARIO DE PRUEBA CREADO: admin / admin123");
            } catch (Exception e) {
                // Si por alguna razón de concurrencia falla, simplemente lo ignoramos
                // ya que significa que el usuario ya fue creado por otro hilo
                System.out.println(">>>> El usuario ya existe o hubo un conflicto menor: " + e.getMessage());
            }
        }
    }
}
package com.projectmanager.infrastructure.adapter.out.persistence;

import com.projectmanager.domain.model.User;
import com.projectmanager.domain.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserRepositoryPort {
    private final JpaUserRepository jpaRepository;

    @Override
    public Optional<User> findByUsername(String username) {
        // Suponiendo que tienes un UserMapper similar al de Project
        return jpaRepository.findByUsername(username)
                .map(entity -> new User(entity.getId(), entity.getUsername(), entity.getEmail(), entity.getPassword()));
    }

    @Override
    public User save(User user) {
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setUsername(user.getUsername());
        entity.setPassword(user.getPassword());
        entity.setEmail(user.getEmail());
        UserEntity saved = jpaRepository.save(entity);
        return new User(saved.getId(), saved.getUsername(), saved.getEmail(), saved.getPassword());
    }
}
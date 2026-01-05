package com.projectmanager.domain.port.out;

import com.projectmanager.domain.model.User;

import java.util.Optional;

public interface UserRepositoryPort {
    User save(User user);
    Optional<User> findByUsername(String username);
}
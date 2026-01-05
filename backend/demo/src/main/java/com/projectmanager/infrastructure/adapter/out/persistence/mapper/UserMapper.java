package com.projectmanager.infrastructure.adapter.out.persistence.mapper;

import com.projectmanager.domain.model.User;
import com.projectmanager.infrastructure.adapter.out.persistence.UserEntity;

public class UserMapper {
    public static User toDomain(UserEntity entity) {
        if (entity == null) return null;
        return new User(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getPassword()
        );
    }

    public static UserEntity toEntity(User domain) {
        if (domain == null) return null;
        return new UserEntity(
                domain.getId(),
                domain.getUsername(),
                domain.getEmail(),
                domain.getPassword()
        );
    }
}

package com.projectmanager.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface JpaProjectRepository extends JpaRepository<ProjectEntity, UUID> {
    // Busca por el ID del dueño (UUID)
    List<ProjectEntity> findByOwnerId(UUID ownerId);
}
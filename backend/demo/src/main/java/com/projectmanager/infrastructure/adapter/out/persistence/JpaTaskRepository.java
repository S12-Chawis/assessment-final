package com.projectmanager.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaTaskRepository extends JpaRepository<TaskEntity, UUID> {
    List<TaskEntity> findByProjectId(UUID projectId);
    long countByProjectIdAndCompletedFalse(UUID projectId);
}
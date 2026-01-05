package com.projectmanager.domain.port.out;

import com.projectmanager.domain.model.Project;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectRepositoryPort {
    Project save(Project project);
    Optional<Project> findById(UUID id);
    List<Project> findAllByOwnerId(UUID ownerId);
}


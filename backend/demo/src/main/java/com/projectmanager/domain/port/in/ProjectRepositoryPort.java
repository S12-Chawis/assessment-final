package com.projectmanager.domain.port.in;

import com.projectmanager.domain.model.Project;

import java.util.List;
import java.util.UUID;

public interface ProjectRepositoryPort {
    Project save(Project project);
    List<Project> findByOwnerId(UUID ownerId);
}
package com.projectmanager.application.service;

import com.projectmanager.domain.model.Project;
import com.projectmanager.domain.model.ProjectStatus;
import com.projectmanager.domain.port.in.CreateProjectUseCase;
import com.projectmanager.domain.port.in.GetProjectsUseCase;
import com.projectmanager.domain.port.out.ProjectRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectService implements CreateProjectUseCase, GetProjectsUseCase {

    private final ProjectRepositoryPort projectRepository;
    private final CurrentUserPort currentUserPort;

    @Override
    public Project create(String name) {
        Project project = new Project();
        project.setId(UUID.randomUUID());
        project.setName(name);
        project.setOwnerId(currentUserPort.getCurrentUserId()); // Asignamos dueño [cite: 298]
        project.setStatus(ProjectStatus.DRAFT); // Estado inicial [cite: 215]
        project.setDeleted(false);

        return projectRepository.save(project);
    }

    @Override
    public List<Project> getAllForCurrentUser() {
        UUID ownerId = currentUserPort.getCurrentUserId();
        return projectRepository.findByOwnerId(ownerId);
    }
}
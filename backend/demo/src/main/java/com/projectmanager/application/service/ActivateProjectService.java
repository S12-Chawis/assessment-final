package com.projectmanager.application.service;

import com.projectmanager.domain.model.Project;
import com.projectmanager.domain.port.out.AuditLogPort;
import com.projectmanager.domain.port.out.ProjectRepositoryPort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ActivateProjectService implements ActivateProjectUseCase { // Puerto de entrada [cite: 64]
    private final ProjectRepositoryPort projectRepository;
    private final TaskRepositoryPort taskRepository;
    private final AuditLogPort auditLog;
    private final NotificationPort notification;
    private final CurrentUserPort currentUser;

    @Override
    public void activate(UUID projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));

        // Regla 2: Solo el dueño puede modificar [cite: 110]
        if (!project.getOwnerId().equals(currentUser.getCurrentUserId())) {
            throw new AccessDeniedException("Only the owner can activate this project");
        }

        long activeTasks = taskRepository.countActiveTasksByProjectId(projectId);
        project.activate(activeTasks); // Regla 1 [cite: 109]

        projectRepository.save(project);

        // Reglas 5 y 6: Auditoría y Notificación [cite: 113, 114]
        auditLog.register("PROJECT_ACTIVATED", projectId);
        notification.notify("Project " + project.getName() + " is now active!");
    }
}
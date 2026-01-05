package com.projectmanager.application.service;

import com.projectmanager.domain.exception.AccessDeniedException;
import com.projectmanager.domain.model.Project;
import com.projectmanager.domain.model.ProjectStatus;
import com.projectmanager.domain.model.Task;
import com.projectmanager.domain.port.in.*;
import com.projectmanager.domain.port.out.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectTaskService implements
        CreateProjectUseCase, GetProjectsUseCase,
        CreateTaskUseCase, CompleteTaskUseCase, ActivateProjectUseCase {

    private final ProjectRepositoryPort projectRepository;
    private final TaskRepositoryPort taskRepository;
    private final CurrentUserPort currentUser;
    private final AuditLogPort auditLog;
    private final NotificationPort notification;

    public void activate(UUID projectId) {
        Project project = findProjectAndValidateOwnership(projectId);

        // REGLA: Al menos una tarea activa (no completada) para activar
        long activeTasks = taskRepository.countByProjectIdAndCompletedFalse(projectId);

        project.activate(activeTasks);

        projectRepository.save(project);

        // REGLA: Generar auditoría y notificación
        auditLog.register("PROJECT_ACTIVATED", projectId);
        notification.notify("Your project '" + project.getName() + "' is now ACTIVE.");
    }

    // REGLA: Listar solo proyectos del usuario actual
    @Override
    public List<Project> getAllForCurrentUser() {
        return projectRepository.findAllByOwnerId(currentUser.getCurrentUserId());
    }

    @Override
    public Project create(String name) {
        Project project = new Project(UUID.randomUUID(), currentUser.getCurrentUserId(), name, ProjectStatus.DRAFT, false);
        return projectRepository.save(project);
    }

    @Override
    public Task create(UUID projectId, String title) {
        validateOwnership(projectId);
        Task task = new Task(UUID.randomUUID(), projectId, title, false, false);
        return taskRepository.save(task);
    }

    @Override
    public void completeTask(UUID taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow();
        validateOwnership(task.getProjectId());

        if (task.isCompleted()) throw new IllegalStateException("Task already completed");

        task.setCompleted(true);
        taskRepository.save(task);
        auditLog.register("TASK_COMPLETED", taskId);
    }

    // Método privado para reutilizar la lógica de seguridad 403
    private void validateOwnership(UUID projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow();
        if (!project.getOwnerId().equals(currentUser.getCurrentUserId())) {
            throw new AccessDeniedException("Forbidden: You are not the owner");
        }
    }

    private Project findProjectAndValidateOwnership(UUID projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        if (!project.getOwnerId().equals(currentUser.getCurrentUserId())) {
            // Usamos la nueva excepción
            throw new AccessDeniedException("403 Forbidden: You do not own this project");
        }
        return project;
    }
}
package com.projectmanager.application.service;

import com.projectmanager.domain.model.Project;
import com.projectmanager.domain.model.ProjectStatus;
import com.projectmanager.domain.model.Task;
import com.projectmanager.domain.port.in.ActivateProjectUseCase;
import com.projectmanager.domain.port.in.CompleteTaskUseCase;
import com.projectmanager.domain.port.in.CreateTaskUseCase;
import com.projectmanager.domain.port.out.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectTaskService implements CreateTaskUseCase, CompleteTaskUseCase, ActivateProjectUseCase {

    private final ProjectRepositoryPort projectRepository;
    private final TaskRepositoryPort taskRepository;
    private final AuditLogPort auditLog;
    private final NotificationPort notification;
    private final CurrentUserPort currentUser;

    @Override
    public Task create(UUID projectId, String title) {
        Project project = findProjectAndValidateOwnership(projectId);

        Task task = new Task(UUID.randomUUID(), projectId, title, false, false);
        return taskRepository.save(task);
    }

    @Override
    public void activate(UUID projectId) {
        Project project = findProjectAndValidateOwnership(projectId);

        // REGLA: Al menos una tarea activa (no completada) para activar
        long activeTasks = taskRepository.countByProjectIdAndCompletedFalse(projectId);
        if (activeTasks == 0) {
            throw new IllegalStateException("Cannot activate project without at least one active task.");
        }

        project.setStatus(ProjectStatus.ACTIVE);
        projectRepository.save(project);

        // REGLA: Generar auditoría y notificación
        auditLog.register("PROJECT_ACTIVATED", projectId);
        notification.notify("Your project '" + project.getName() + "' is now ACTIVE.");
    }

    @Override
    public void completeTask(UUID taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        // REGLA: Verificar propiedad a través del proyecto
        findProjectAndValidateOwnership(task.getProjectId());

        // REGLA: Una tarea completada no puede modificarse
        if (task.isCompleted()) {
            throw new IllegalStateException("Task is already completed and cannot be modified.");
        }

        task.setCompleted(true);
        taskRepository.save(task);

        // REGLA: Auditoría y notificación
        auditLog.register("TASK_COMPLETED", taskId);
        notification.notify("Task '" + task.getTitle() + "' has been marked as completed.");
    }

    private Project findProjectAndValidateOwnership(UUID projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        // REGLA: Solo el propietario puede modificar [403 Forbidden]
        if (!project.getOwnerId().equals(currentUser.getCurrentUserId())) {
            throw new RuntimeException("Access Denied: You are not the owner of this project.");
        }
        return project;
    }
}
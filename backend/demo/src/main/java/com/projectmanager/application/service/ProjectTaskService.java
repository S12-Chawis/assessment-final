package com.projectmanager.application.service;

import com.projectmanager.domain.model.Project;
import com.projectmanager.domain.model.ProjectStatus;
import com.projectmanager.domain.model.Task;
import com.projectmanager.domain.port.out.AuditLogPort;
import com.projectmanager.domain.port.out.ProjectRepositoryPort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
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
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));

        // Regla 2: Solo el propietario puede modificar [cite: 110]
        if (!project.getOwnerId().equals(currentUser.getCurrentUserId())) {
            throw new AccessDeniedException("403 Forbidden: Not the owner");
        }

        Task task = new Task(UUID.randomUUID(), projectId, title, false, false);
        return taskRepository.save(task);
    }

    @Override
    public void activate(UUID projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));

        if (!project.getOwnerId().equals(currentUser.getCurrentUserId())) {
            throw new AccessDeniedException("403 Forbidden: Not the owner"); // [cite: 110]
        }

        // Regla 1: Al menos una tarea activa [cite: 109]
        long activeTasks = taskRepository.countByProjectIdAndCompletedFalse(projectId);
        if (activeTasks == 0) {
            throw new IllegalStateException("Cannot activate project without active tasks");
        }

        project.setStatus(ProjectStatus.ACTIVE);
        projectRepository.save(project);

        auditLog.register("PROJECT_ACTIVATED", projectId); // [cite: 113]
        notification.notify("Project " + project.getName() + " is now ACTIVE"); // [cite: 114]
    }

    @Override
    public void completeTask(UUID taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));

        // Regla 3: Tarea completada no puede modificarse [cite: 111]
        if (task.isCompleted()) {
            throw new IllegalStateException("Task is already completed");
        }

        task.setCompleted(true);
        taskRepository.save(task);

        auditLog.register("TASK_COMPLETED", taskId); // [cite: 113]
        notification.notify("Task " + task.getTitle() + " completed"); // [cite: 114]
    }
}
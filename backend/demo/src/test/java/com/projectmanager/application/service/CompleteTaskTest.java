package com.projectmanager.application.service;

import com.projectmanager.domain.model.Project;
import com.projectmanager.domain.model.ProjectStatus;
import com.projectmanager.domain.model.Task;
import com.projectmanager.domain.port.out.AuditLogPort;
import com.projectmanager.domain.port.out.CurrentUserPort;
import com.projectmanager.domain.port.out.ProjectRepositoryPort;
import com.projectmanager.domain.port.out.TaskRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue; // IMPORT CORRECTO PARA ASSERT
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompleteTaskTest {
    @Mock private TaskRepositoryPort taskRepo;
    @Mock
    private ProjectRepositoryPort projectRepo;
    @Mock private CurrentUserPort currentUser;
    @Mock private AuditLogPort auditLog;

    @InjectMocks
    private ProjectTaskService service;

    @Test
    void completeTask_ShouldMarkAsCompleted_WhenOwner() {
        UUID taskId = UUID.randomUUID();
        UUID projectId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        Task task = new Task(taskId, projectId, "Task 1", false, false);
        Project project = new Project(projectId, userId, "Project 1", ProjectStatus.ACTIVE, false);

        when(taskRepo.findById(taskId)).thenReturn(Optional.of(task));
        when(projectRepo.findById(projectId)).thenReturn(Optional.of(project));
        when(currentUser.getCurrentUserId()).thenReturn(userId);

        service.completeTask(taskId);

        assertTrue(task.isCompleted());
        verify(taskRepo).save(task);
    }
}
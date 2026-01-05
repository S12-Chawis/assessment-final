package com.projectmanager.application.service;

import com.projectmanager.domain.model.Project;
import com.projectmanager.domain.model.ProjectStatus;
import com.projectmanager.domain.port.out.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

// ESTOS SON LOS IMPORTS CORRECTOS
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActivateProjectTest {
    @Mock private ProjectRepositoryPort projectRepo;
    @Mock private TaskRepositoryPort taskRepo;
    @Mock private AuditLogPort audit;
    @Mock private NotificationPort notify;
    @Mock private CurrentUserPort user;

    @InjectMocks
    private ProjectTaskService service;

    @Test
    void activateProject_WithTasks_ShouldSucceed() {
        UUID pid = UUID.randomUUID();
        UUID uid = UUID.randomUUID();
        Project p = new Project(pid, uid, "Test", ProjectStatus.DRAFT, false);

        when(projectRepo.findById(pid)).thenReturn(Optional.of(p));
        when(user.getCurrentUserId()).thenReturn(uid);
        when(taskRepo.countByProjectIdAndCompletedFalse(pid)).thenReturn(1L);

        service.activate(pid);

        // Ahora verify y any funcionarán correctamente
        verify(projectRepo).save(any(Project.class));
        verify(notify).notify(anyString());
    }

    @Test
    void activateProject_WithoutTasks_ShouldFail() {
        UUID pid = UUID.randomUUID();
        UUID uid = UUID.randomUUID();
        Project p = new Project(pid, uid, "Empty Project", ProjectStatus.DRAFT, false);

        when(projectRepo.findById(pid)).thenReturn(Optional.of(p));
        when(user.getCurrentUserId()).thenReturn(uid);
        when(taskRepo.countByProjectIdAndCompletedFalse(pid)).thenReturn(0L); // 0 tareas

        assertThrows(IllegalStateException.class, () -> service.activate(pid));
    }
}
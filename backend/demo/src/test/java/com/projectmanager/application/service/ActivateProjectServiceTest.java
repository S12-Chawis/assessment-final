package com.projectmanager.application.service;

import com.projectmanager.domain.model.Project;
import com.projectmanager.domain.model.ProjectStatus;
import com.projectmanager.domain.port.out.AuditLogPort;
import com.projectmanager.domain.port.out.ProjectRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

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
    void activateProject_WithoutTasks_ShouldFail() {
        UUID pid = UUID.randomUUID();
        UUID uid = UUID.randomUUID();
        Project p = new Project(pid, uid, "Test", ProjectStatus.DRAFT, false);

        when(projectRepo.findById(pid)).thenReturn(Optional.of(p));
        when(user.getCurrentUserId()).thenReturn(uid);
        when(taskRepo.countByProjectIdAndCompletedFalse(pid)).thenReturn(0L);

        assertThrows(IllegalStateException.class, () -> service.activate(pid)); //
    }
}
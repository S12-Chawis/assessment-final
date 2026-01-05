package com.projectmanager.domain.port.in;

import java.util.UUID;
public interface CompleteTaskUseCase {
    void completeTask(UUID taskId);
}
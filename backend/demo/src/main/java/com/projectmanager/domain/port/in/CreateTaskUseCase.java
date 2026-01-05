package com.projectmanager.domain.port.in;

import com.projectmanager.domain.model.Task;
import java.util.UUID;
public interface CreateTaskUseCase {
    Task create(UUID projectId, String title);
}
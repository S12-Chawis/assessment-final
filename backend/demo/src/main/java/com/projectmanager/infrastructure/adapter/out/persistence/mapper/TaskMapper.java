package com.projectmanager.infrastructure.adapter.out.persistence.mapper;

import com.projectmanager.domain.model.Task;
import com.projectmanager.infrastructure.adapter.out.persistence.TaskEntity;

public class TaskMapper {
    public static Task toDomain(TaskEntity entity) {
        if (entity == null) return null;
        return new Task(entity.getId(), entity.getProjectId(), entity.getTitle(), entity.isCompleted(), entity.isDeleted());
    }

    public static TaskEntity toEntity(Task domain) {
        if (domain == null) return null;
        return new TaskEntity(domain.getId(), domain.getProjectId(), domain.getTitle(), domain.isCompleted(), domain.isDeleted());
    }
}
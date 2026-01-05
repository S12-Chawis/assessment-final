package com.projectmanager.infrastructure.adapter.out.persistence;

import com.projectmanager.domain.model.Task;
import com.projectmanager.domain.port.out.TaskRepositoryPort;
import com.projectmanager.infrastructure.adapter.out.persistence.mapper.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TaskPersistenceAdapter implements TaskRepositoryPort {
    private final JpaTaskRepository repository;

    @Override
    public Task save(Task task) {
        return TaskMapper.toDomain(repository.save(TaskMapper.toEntity(task)));
    }

    @Override
    public Optional<Task> findById(UUID id) {
        return repository.findById(id).map(TaskMapper::toDomain);
    }

    @Override
    public long countByProjectIdAndCompletedFalse(UUID projectId) {
        return repository.countByProjectIdAndCompletedFalse(projectId);
    }
}
package com.projectmanager.domain.port.out;

import com.projectmanager.domain.model.Task;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepositoryPort {
    Task save(Task task);
    Optional<Task> findById(UUID id);
    // Necesario para la regla de negocio: contar tareas activas para activar proyecto
    long countByProjectIdAndCompletedFalse(UUID projectId);
}
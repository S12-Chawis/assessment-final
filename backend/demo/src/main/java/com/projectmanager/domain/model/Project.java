package com.projectmanager.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    private UUID id;
    private UUID ownerId;
    private String name;
    private ProjectStatus status; // DRAFT, ACTIVE
    private boolean deleted;

    // Regla de Negocio 1: Solo se activa si tiene tareas [cite: 109]
    public void activate(long activeTasksCount) {
        if (activeTasksCount == 0) {
            throw new IllegalStateException("Project must have at least one active task to be activated.");
        } else {
            this.status = ProjectStatus.ACTIVE;
        }
    }
}

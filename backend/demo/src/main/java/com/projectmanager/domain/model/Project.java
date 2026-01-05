package com.projectmanager.domain.model;

import java.util.UUID;

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
        }
        this.status = ProjectStatus.ACTIVE;
    }
}

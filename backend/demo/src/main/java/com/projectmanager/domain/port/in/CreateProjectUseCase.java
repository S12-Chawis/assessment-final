package com.projectmanager.domain.port.in;

import com.projectmanager.domain.model.Project;

public interface CreateProjectUseCase {
    Project create(String name);
}
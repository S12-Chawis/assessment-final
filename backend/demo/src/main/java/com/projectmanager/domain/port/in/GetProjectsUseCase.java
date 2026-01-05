package com.projectmanager.domain.port.in;

import com.projectmanager.domain.model.Project;

import java.util.List;

public interface GetProjectsUseCase {
    List<Project> getAllForCurrentUser();
}
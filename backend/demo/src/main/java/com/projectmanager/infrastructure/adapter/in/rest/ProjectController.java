package com.projectmanager.infrastructure.adapter.in.rest;

import com.projectmanager.domain.model.Project;
import com.projectmanager.domain.port.in.ActivateProjectUseCase;
import com.projectmanager.domain.port.in.CreateProjectUseCase;
import com.projectmanager.domain.port.in.GetProjectsUseCase;
import com.projectmanager.infrastructure.adapter.in.rest.dto.ProjectRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@Tag(name = "Projects", description = "Project management endpoints") // Swagger
public class ProjectController {

    private final CreateProjectUseCase createProjectUseCase;
    private final ActivateProjectUseCase activateProjectUseCase;
    private final GetProjectsUseCase getProjectsUseCase;

    @PostMapping
    public ResponseEntity<Project> create(@RequestBody ProjectRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createProjectUseCase.create(request.name()));
    }

    @GetMapping
    public ResponseEntity<List<Project>> getAll() {
        return ResponseEntity.ok(getProjectsUseCase.getAllForCurrentUser());
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activate(@PathVariable java.util.UUID id) {
        activateProjectUseCase.activate(id);
        return ResponseEntity.noContent().build();
    }
}

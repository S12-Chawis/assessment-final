package com.projectmanager.infrastructure.adapter.in.rest;

import com.projectmanager.domain.port.in.CompleteTaskUseCase;
import com.projectmanager.domain.port.in.CreateTaskUseCase;
import com.projectmanager.domain.model.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final CreateTaskUseCase createTaskUseCase;
    private final CompleteTaskUseCase completeTaskUseCase;

    @PostMapping("/{projectId}")
    public ResponseEntity<Task> create(@PathVariable UUID projectId, @RequestBody String title) {
        return ResponseEntity.ok(createTaskUseCase.create(projectId, title));
    }

    @PatchMapping("/{taskId}/complete")
    public ResponseEntity<Void> complete(@PathVariable UUID taskId) {
        completeTaskUseCase.completeTask(taskId);
        return ResponseEntity.noContent().build();
    }
}
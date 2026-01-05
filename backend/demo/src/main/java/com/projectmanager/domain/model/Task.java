package com.projectmanager.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    private UUID id;
    private UUID projectId;
    private String title;
    private boolean completed; // [cite: 33]
    private boolean deleted;   // [cite: 34]
}
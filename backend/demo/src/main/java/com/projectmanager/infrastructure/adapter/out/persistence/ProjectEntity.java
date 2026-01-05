package com.projectmanager.infrastructure.adapter.out.persistence;

import com.projectmanager.domain.model.ProjectStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.UUID;

@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE projects SET deleted = true WHERE id=?") // Soft Delete
@Where(clause = "deleted = false") // Filtra los borrados automáticamente
public class ProjectEntity {
    @Id
    private UUID id;
    private UUID ownerId;
    private String name;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    private boolean deleted = false;
}
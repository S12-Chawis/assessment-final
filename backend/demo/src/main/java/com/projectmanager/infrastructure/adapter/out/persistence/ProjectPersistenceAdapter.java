package com.projectmanager.infrastructure.adapter.out.persistence;

import com.projectmanager.domain.model.Project;
import com.projectmanager.domain.port.out.ProjectRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProjectPersistenceAdapter implements ProjectRepositoryPort {
    private final JpaProjectRepository jpaRepository;

    @Override
    public List<Project> findByOwnerId(UUID ownerId) {
        // 1. Llamamos al repositorio de Spring Data
        // 2. Convertimos la lista de Entidades a lista de Dominio usando el Mapper
        return jpaRepository.findByOwnerId(ownerId).stream()
                .map(ProjectMapper::toDomain)
                .toList();
    }

    @Override
    public Project save(Project project) {
        ProjectEntity entity = ProjectMapper.toEntity(project);
        return ProjectMapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<Project> findById(UUID id) {
        return jpaRepository.findById(id).map(ProjectMapper::toDomain);
    }
}

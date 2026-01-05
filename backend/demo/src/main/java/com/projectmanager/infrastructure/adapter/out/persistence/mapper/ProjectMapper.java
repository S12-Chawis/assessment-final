package com.projectmanager.infrastructure.adapter.out.persistence.mapper;


import com.projectmanager.domain.model.Project;
import com.projectmanager.infrastructure.adapter.out.persistence.ProjectEntity;

public class ProjectMapper {
    public static Project toDomain(ProjectEntity entity) {
        if (entity == null) return null;
        return new Project(
                entity.getId(),
                entity.getOwnerId(),
                entity.getName(),
                entity.getStatus(),
                entity.isDeleted()
        );
    }

    public static ProjectEntity toEntity(Project domain) {
        if (domain == null) return null;
        ProjectEntity entity = new ProjectEntity();
        entity.setId(domain.getId());
        entity.setOwnerId(domain.getOwnerId());
        entity.setName(domain.getName());
        entity.setStatus(domain.getStatus());
        entity.setDeleted(domain.isDeleted());
        return entity;
    }
}
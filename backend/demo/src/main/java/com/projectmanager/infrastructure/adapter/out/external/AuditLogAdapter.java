package com.projectmanager.infrastructure.adapter.out.external;

import com.projectmanager.domain.port.out.AuditLogPort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class AuditLogAdapter implements AuditLogPort {
    @Override
    public void register(String action, UUID entityId) {
        // Requerimiento: Generar auditoría [cite: 113]
        System.out.println("[AUDIT] Action: " + action + " | Entity ID: " + entityId + " | Timestamp: " + LocalDateTime.now());
    }
}
package com.projectmanager.infrastructure.adapter.out.external;

import com.projectmanager.domain.port.out.NotificationPort;
import org.springframework.stereotype.Component;

@Component
public class NotificationAdapter implements NotificationPort {
    @Override
    public void notify(String message) {
        // Requerimiento: Generar notificación [cite: 114]
        System.out.println("[NOTIFICATION]: " + message);
    }
}

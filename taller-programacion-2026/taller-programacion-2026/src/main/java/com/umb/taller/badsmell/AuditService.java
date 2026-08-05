package com.umb.taller.badsmell;

public class AuditService {
    public void logActivity(String action) {
        if (action == null || action.trim().isEmpty()) {
            throw new IllegalArgumentException("La acción es obligatoria");
        }

        System.out.println("[LOG] " + action + " performed at " + System.currentTimeMillis());
    }
}

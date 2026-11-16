package ru.student.gallery.config;

import java.util.HashMap;
import java.util.Map;

public class ServiceRegistry {
    private final Map<String, Object> services = new HashMap<>();

    public void register(String name, Object service) {
        services.put(name, service);
    }

    @SuppressWarnings("unchecked")
    public <T> T getService(String name, Class<T> type) {
        Object service = services.get(name);
        if (service == null) {
            throw new IllegalStateException("Service not found: " + name);
        }
        return (T) service;
    }
}

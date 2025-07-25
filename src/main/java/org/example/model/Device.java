package org.example.model;

import java.time.LocalDateTime;

public class Device {
    private String id;
    private String name;
    private String type;
    private boolean isActive;
    private long lastCommunication; // Epoch millis

    public Device(String id, String name, String type, boolean isActive, long lastCommunication) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.isActive = isActive;
        this.lastCommunication = lastCommunication;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public long getLastCommunication() {
        return lastCommunication;
    }

    public void setLastCommunication(long lastCommunication) {
        this.lastCommunication = lastCommunication;
    }
}


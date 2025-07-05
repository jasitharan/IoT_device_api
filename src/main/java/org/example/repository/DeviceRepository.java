package org.example.repository;

import org.example.model.Device;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DeviceRepository
{
    // Concurrent Hash Map for Thread Safety
    private final Map<String, Device> devices = new ConcurrentHashMap<>();

    public List<Device> findAll() {
        return new ArrayList<>(devices.values());
    }

    public Device findById(String id) {
        return devices.get(id);
    }

    public void save(Device device) {
        devices.put(device.getId(), device);
    }

    public boolean update(String id, Device device) {
        if (devices.containsKey(id)) {
            devices.put(id, device);
            return true;
        }
        return false;
    }

    public boolean delete(String id) {
        return devices.remove(id) != null;
    }

}

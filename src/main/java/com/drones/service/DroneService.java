package com.drones.service;


import com.drones.model.BatteryLevel;
import com.drones.model.Drone;
import com.drones.repository.BatteryLevelRepository;
import com.drones.repository.DroneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;

@Service
public class DroneService
{
    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private BatteryLevelRepository batteryLevelRepository;

    public Drone register(Drone drone)
    {
        return droneRepository.save(drone);
    }

    public List<Drone> available(String state)
    {
        return droneRepository.findAllByState(state);
    }

    public Drone get(String serialNumber)
    {
        Drone drone = droneRepository.findBySerialNumber(serialNumber);
        return drone;
    }

    @Scheduled(initialDelayString = "PT30S", fixedDelayString = "PT5M")
    public void batteryLevels()
    {
        System.out.println("Checking Battery Levels: " + Instant.now());
        List<Drone> drones = droneRepository.findAll();
        for (Drone drone : drones)
        {
            // Save Drone Battery Level
            batteryLevelRepository.save(new BatteryLevel(
                    drone.getSerialNumber(),
                    drone.getState(),
                    drone.getBatteryCapacity()
            ));
        }
    }
}


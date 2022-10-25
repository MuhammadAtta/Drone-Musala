package com.drones.repository;


import com.drones.model.BatteryLevel;
import com.drones.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatteryLevelRepository  extends JpaRepository<BatteryLevel, Long>
{
}

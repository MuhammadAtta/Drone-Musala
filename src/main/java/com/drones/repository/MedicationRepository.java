package com.drones.repository;


import com.drones.model.Drone;
import com.drones.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MedicationRepository extends JpaRepository<Medication, Long>
{


    @Query(value = "SELECT m from Medication m where m.code = :code ")
    Medication findByCode(@Param("code") String code);
}

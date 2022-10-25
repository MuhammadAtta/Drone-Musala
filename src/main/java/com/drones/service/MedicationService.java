package com.drones.service;


import com.drones.model.Drone;
import com.drones.model.Medication;
import com.drones.repository.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicationService
{
    @Autowired
    private MedicationRepository medicationRepository;

    public Medication register(Medication medication)
    {
        return medicationRepository.save(medication);
    }

    public List<Medication> findAll()
    {
        return medicationRepository.findAll();
    }

    public Medication get(String code)
    {
        Medication medication = medicationRepository.findByCode(code);
        return medication;
    }
}

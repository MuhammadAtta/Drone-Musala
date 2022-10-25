package com.drones.controller;


import com.drones.data.response.ApiResponse;
import com.drones.model.Drone;
import com.drones.model.Medication;
import com.drones.service.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/medication/")
public class MedicationController
{

    @Autowired
    private MedicationService medicationService;

    @PostMapping("register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody Medication param)
    {
        Medication medication = medicationService.register(param);
        ApiResponse response = new ApiResponse("success", "Medication created successfully", medication);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("list")
    public ResponseEntity<ApiResponse> list()
    {
        List<Medication> medications =  medicationService.findAll();
        ApiResponse response = new ApiResponse("success", "Medications loaded successfully", medications);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}


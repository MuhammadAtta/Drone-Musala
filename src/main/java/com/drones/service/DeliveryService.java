package com.drones.service;


import com.drones.data.request.DeliveryRequest;
import com.drones.data.response.DeliveryResponse;
import com.drones.data.response.DroneItemResponse;
import com.drones.model.Delivery;
import com.drones.model.DeliveryItem;
import com.drones.model.Medication;
import com.drones.repository.DeliveryItemRepository;
import com.drones.repository.DeliveryRepository;
import com.drones.repository.DroneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class DeliveryService
{

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private DeliveryItemRepository deliveryItemRepository;

    @Autowired
    private DroneRepository droneRepository;

    public DeliveryResponse load(DeliveryRequest param)
    {
        //Update Drone
        droneRepository.setState("LOADING", param.getSerialNumber());

        List<DeliveryItem> items = new ArrayList<>();

        //Create Delivery
        Delivery delivery = deliveryRepository.save(new Delivery(param.getSerialNumber(), param.getSource(), param.getDestination()));

        //Save Items
        for(String code : param.getMedications())
        {
            DeliveryItem deliveryItem = deliveryItemRepository.save(new DeliveryItem(delivery.getId(), code));
            items.add(deliveryItem);
        }

        //Update Drone
        droneRepository.setState("LOADED", param.getSerialNumber());

        return new DeliveryResponse(delivery, items);
    }

    public DroneItemResponse items(String serialNumber)
    {
        List<Medication> medications = new ArrayList<>();

        Delivery delivery = deliveryRepository.findByDrone(serialNumber);
        if(delivery != null)
        {
            medications = deliveryItemRepository.findAllMedicationByDeliveryId(delivery.getId());
        }

        return new DroneItemResponse(delivery, medications);
    }

    public Delivery get(String serialNumber)
    {
        return deliveryRepository.findByDrone(serialNumber);
    }

    public DeliveryResponse dispatch(Delivery delivery)
    {
        //Update Delivery and Drone
        deliveryRepository.setDispatched(Instant.now(), delivery.getId());
        droneRepository.setState("DELIVERING", delivery.getDrone());

        //Get Data
        delivery.setDispatchedAt(Instant.now());
        List<DeliveryItem> items = deliveryItemRepository.findAllByDeliveryId(delivery.getId());

        return new DeliveryResponse(delivery, items);
    }

    public DeliveryResponse delivered(Delivery delivery)
    {
        //Update Delivery and Drone
        deliveryRepository.setDelivered(Instant.now(), delivery.getId());
        droneRepository.setState("IDLE", delivery.getDrone());

        //Get Data
        delivery.setDeliveredAt(Instant.now());
        List<DeliveryItem> items = deliveryItemRepository.findAllByDeliveryId(delivery.getId());

        return new DeliveryResponse(delivery, items);
    }
}

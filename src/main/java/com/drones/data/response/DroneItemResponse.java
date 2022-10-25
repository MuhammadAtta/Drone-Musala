package com.drones.data.response;


import com.drones.model.Delivery;
import com.drones.model.Drone;
import com.drones.model.Medication;

import java.time.Instant;
import java.util.List;

public class DroneItemResponse
{
    public DroneItemResponse()
    {

    }

    public DroneItemResponse(Delivery delivery, List<Medication> items) {
        this.delivery = delivery;
        this.items = items;
    }

    private Delivery delivery;
    private List<Medication> items;

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public List<Medication> getitems() {
        return items;
    }

    public void setitems(List<Medication> items) {
        this.items = items;
    }
}

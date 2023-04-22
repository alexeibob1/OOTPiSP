package com.lab2.trains;

import java.io.Serializable;

public class DieselTrain extends RailTransport implements Serializable {
    private Fuel fuel;

    public Fuel getFuel() {
        return fuel;
    }

    public void setFuel(Fuel fuel) {
        this.fuel = fuel;
    }

    public DieselTrain(int id, int totalPlaces, int maxSpeed, Schedule schedule, Driver driver, Fuel fuel) {
        super(id, totalPlaces, maxSpeed, schedule, driver);
        this.fuel = fuel;
        this.setInfoProperty(this.toString());
    }

    public DieselTrain() {
        super();
    }

    @Override
    public void setInfoProperty(String additionalInfo) {
        super.setInfoProperty(additionalInfo);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(".\nFuel type: ");
        sb.append(this.getFuel());
        return sb.toString();
    }
}

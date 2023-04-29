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
        this.setInfoProperty();
    }

    public DieselTrain() {
        super();
    }

    @Override
    public void setInfoProperty() {
        super.setInfoProperty();
    }

    @Override
    public String toTableString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toTableString());
        sb.append(".\nFuel type: ");
        sb.append(this.getFuel());
        return sb.toString();
    }
}

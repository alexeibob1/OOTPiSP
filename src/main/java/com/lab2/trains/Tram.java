package com.lab2.trains;

import java.io.Serializable;

public class Tram extends ElectricTrain implements Serializable {
    private int noisiness;

    public int getNoisiness() {
        return noisiness;
    }

    public void setNoisiness(int noisiness) {
        this.noisiness = noisiness;
    }

    public Tram(int id, int totalPlaces, int maxSpeed, Schedule schedule, Driver driver, int voltage, PowerSupply powerSupply, int noisiness) {
        super(id, totalPlaces, maxSpeed, schedule, driver, voltage, powerSupply);
        this.noisiness = noisiness;
        this.setInfoProperty();
    }

    public Tram() {}

    @Override
    public void setInfoProperty() {
        super.setInfoProperty();
    }

    @Override
    public String toTableString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toTableString());
        sb.append(".\nNoisiness: ");
        sb.append(this.getNoisiness());
        return sb.toString();
    }
}

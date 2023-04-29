package com.lab2.trains;

import java.io.Serializable;

public class ElectricTrain extends RailTransport implements Serializable {
    private int voltage;
    private PowerSupply powerSupply;

    public int getVoltage() {
        return voltage;
    }

    public void setVoltage(int voltage) {
        this.voltage = voltage;
    }

    public PowerSupply getPowerSupply() {
        return powerSupply;
    }

    public void setPowerSupply(PowerSupply powerSupply) {
        this.powerSupply = powerSupply;
    }

    public ElectricTrain(int id, int totalPlaces, int maxSpeed, Schedule schedule, Driver driver, int voltage, PowerSupply powerSupply) {
        super(id, totalPlaces, maxSpeed, schedule, driver);
        this.voltage = voltage;
        this.powerSupply = powerSupply;
        this.setInfoProperty();
    }

    public ElectricTrain() {super();}

    @Override
    public void setInfoProperty() {
        super.setInfoProperty();
    }

    @Override
    public String toTableString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toTableString());
        sb.append(".\n");
        sb.append("Voltage: ");
        sb.append(this.getVoltage());
        sb.append(". Power supply: ");
        sb.append(this.getPowerSupply());
        return sb.toString();
    }
}

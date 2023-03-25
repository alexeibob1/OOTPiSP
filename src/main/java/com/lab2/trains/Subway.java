package com.lab2.trains;

public class Subway extends ElectricTrain {
    private int depth;

    public int getDepth() {
        return depth;
    }

    public Subway(int id, int totalPlaces, int maxSpeed, Schedule schedule, Driver driver, int voltage, PowerSupply powerSupply, int depth) {
        super(id, totalPlaces, maxSpeed, schedule, driver, voltage, powerSupply);
        this.depth = depth;
        this.setInfoProperty(this.toString());
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    @Override
    public void setInfoProperty(String additionalInfo) {
        super.setInfoProperty(additionalInfo);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(".\nDepth: ");
        sb.append(this.getDepth());
        return sb.toString();
    }
}

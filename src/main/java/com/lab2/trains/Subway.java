package com.lab2.trains;

import java.io.Serializable;

public class Subway extends ElectricTrain implements Serializable {
    private int depth;

    public int getDepth() {
        return depth;
    }

    public Subway(int id, int totalPlaces, int maxSpeed, Schedule schedule, Driver driver, int voltage, PowerSupply powerSupply, int depth) {
        super(id, totalPlaces, maxSpeed, schedule, driver, voltage, powerSupply);
        this.depth = depth;
        this.setInfoProperty(this.toTableString());
    }

    public Subway() {super();}

    public void setDepth(int depth) {
        this.depth = depth;
    }

    @Override
    public void setInfoProperty(String additionalInfo) {
        super.setInfoProperty(additionalInfo);
    }

    @Override
    public String toTableString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toTableString());
        sb.append(".\nDepth: ");
        sb.append(this.getDepth());
        return sb.toString();
    }

//    @Override
//    public String toString() {
//        return super.toString() + "depth\n" + depth + "\n";
//    }
}

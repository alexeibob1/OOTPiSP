package com.lab2.trains;

import java.io.Serial;
import java.io.Serializable;

public class RailTransport implements Serializable {

    private Schedule schedule;
    private int id;
    private int totalPlaces;
    private int maxSpeed;
    private Driver driver;

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotalPlaces() {
        return totalPlaces;
    }

    public void setTotalPlaces(int totalPlaces) {
        this.totalPlaces = totalPlaces;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public String getTrainInfo() {
        return trainInfo;
    }

    public void setTrainInfo(String trainInfo) {
        this.trainInfo = trainInfo;
    }

    private transient String trainInfo;

    public RailTransport(int id, int totalPlaces, int maxSpeed, Schedule schedule, Driver driver) {
        this.id = id;
        this.totalPlaces = totalPlaces;
        this.maxSpeed = maxSpeed;
        this.schedule = schedule;
        this.driver = driver;
        this.setInfoProperty();
    }

    public RailTransport() {}

    public void setInfoProperty() {
        this.trainInfo = this.toTableString();
    }

    public String toTableString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.schedule.toTableString());
        sb.append(".\n");
        sb.append(this.driver.toTableString());
        sb.append(".\nTotal places: ");
        sb.append(this.totalPlaces);
        sb.append(". Max speed: ");
        sb.append(this.maxSpeed);
        return sb.toString();
    }
}

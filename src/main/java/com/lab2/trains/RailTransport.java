package com.lab2.trains;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class RailTransport {
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

    private String trainInfo;

    public RailTransport(int id, int totalPlaces, int maxSpeed, Schedule schedule, Driver driver) {
        this.id = id;
        this.totalPlaces = totalPlaces;
        this.maxSpeed = maxSpeed;
        this.schedule = schedule;
        this.driver = driver;
        this.setInfoProperty(this.toString());
    }

    public void setInfoProperty(String additionalInfo) {
        this.trainInfo = additionalInfo;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.schedule.toString());
        sb.append(".\n");
        sb.append(this.driver.toString());
        sb.append(".\nTotal places: ");
        sb.append(this.totalPlaces);
        sb.append(". Max speed: ");
        sb.append(this.maxSpeed);
        return sb.toString();
    }
}

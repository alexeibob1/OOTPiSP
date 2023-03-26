package com.lab2.trains;

public class TrainType {
    private Class<?> trainType;
    private String trainName;

    public TrainType(Class<?> trainType, String trainName) {
        this.trainType = trainType;
        this.trainName = trainName;
    }

    public Class<?> getTrainType() {
        return trainType;
    }

    public void setTrainType(Class<?> trainType) {
        this.trainType = trainType;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }
}

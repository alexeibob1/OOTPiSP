package com.lab2.trains;

public class TrainType {
    private Class<?> trainType;
    private String trainName;
    private String fileName;

    public TrainType(Class<?> trainType, String trainName, String fileName) {
        this.trainType = trainType;
        this.trainName = trainName;
        this.fileName = fileName;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
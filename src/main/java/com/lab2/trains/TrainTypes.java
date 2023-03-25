package com.lab2.trains;

public class TrainTypes {
    public static Class<?> getTrainType(String stringTrainType) {
        Class<?> trainType = null;
        switch (stringTrainType) {
            case "Rail Transport" -> trainType = RailTransport.class;
            case "Electric Train" -> trainType = ElectricTrain.class;
            case "Diesel Train" -> trainType = DieselTrain.class;
            case "Subway" -> trainType = Subway.class;
            case "Tram" -> trainType = Tram.class;
        }
        return trainType;
    }
}

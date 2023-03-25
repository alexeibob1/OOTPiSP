package com.lab2.factories;

import com.lab2.trains.*;

public class ConcreteTrainFactory {
    public AbstractTrainFactory getTrain(Class<?> type) {
        AbstractTrainFactory tempFactory = null;
        if (type.equals(RailTransport.class)) {
            tempFactory = new RailTransportFactory();
        } else if (type.equals(ElectricTrain.class)) {
            tempFactory = new ElectricTrainFactory();
        } else if (type.equals(DieselTrain.class)) {
            tempFactory = new DieselTrainFactory();
        } else if (type.equals(Tram.class)) {
            tempFactory = new TramFactory();
        } else if (type.equals(Subway.class)) {
            tempFactory = new SubwayFactory();
        }
        return tempFactory;
    }
}

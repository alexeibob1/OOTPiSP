package com.lab2.factories;

import com.lab2.trains.RailTransport;

public interface AbstractTrainFactory {
    public RailTransport add();
    public void edit(RailTransport train);
}

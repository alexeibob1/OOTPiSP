package com.lab2.factories;

import com.lab2.trains.RailTransport;

public interface AbstractTrainFactory {
    RailTransport add(String formFileName, String formTitle);
    void edit(RailTransport train, String formFileName, String formTitle);
}

package com.lab2.serializers;

import com.lab2.trains.RailTransport;
import javafx.collections.ObservableList;

import java.io.InputStream;
import java.io.OutputStream;

public interface Serializer {
    void serialize(ObservableList<RailTransport> trains, OutputStream outputStream);

    ObservableList<RailTransport> deserialize(InputStream inputStream);
}

package com.lab2.serializers;

import com.lab2.trains.RailTransport;
import javafx.collections.ObservableList;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public interface Serializable {
    void serialize(ObservableList<RailTransport> trains, OutputStream outputStream);

    ObservableList<RailTransport> deserialize(InputStream inputStream);
}

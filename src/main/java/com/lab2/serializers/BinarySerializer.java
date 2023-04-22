package com.lab2.serializers;

import com.lab2.trains.RailTransport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BinarySerializer implements Serializable {
    @Override
    public void serialize(ObservableList<RailTransport> trains, OutputStream outputStream) {
        List<RailTransport> serializableTrainList = new ArrayList<>(trains);
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(serializableTrainList);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ObservableList<RailTransport> deserialize(InputStream inputStream) {
        ArrayList<RailTransport> trains = null;
        ObservableList<RailTransport> res = FXCollections.observableArrayList();

        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            trains = (ArrayList<RailTransport>)objectInputStream.readObject();
            res.addAll(trains);
            objectInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return res;
    }
}

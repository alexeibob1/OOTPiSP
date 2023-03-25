package com.lab2.factories;

import com.lab2.controllers.RailTransportController;
import com.lab2.trains.ElectricTrain;
import com.lab2.trains.RailTransport;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class RailTransportFactory implements AbstractTrainFactory {
    @Override
    public RailTransport add() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addRailTransport.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setTitle("Rail transport");
        RailTransportController controller = loader.getController();
        stage.showAndWait();
        return controller.getRailTransport();
    }

    @Override
    public void edit(RailTransport train) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addRailTransport.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setTitle("Rail transport");
        RailTransportController controller = loader.getController();
        controller.setRailTransport(train);
        stage.showAndWait();
    }
}

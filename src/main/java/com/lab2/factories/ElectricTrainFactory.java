package com.lab2.factories;

import com.lab2.controllers.ElectricTrainController;
import com.lab2.controllers.RailTransportController;
import com.lab2.trains.ElectricTrain;
import com.lab2.trains.RailTransport;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ElectricTrainFactory implements AbstractTrainFactory {
    @Override
    public ElectricTrain add() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addElectricTrain.fxml"));
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
        stage.setTitle("Electric train");
        ElectricTrainController controller = loader.getController();
        stage.showAndWait();
        return controller.getElectricTrain();
    }

    @Override
    public void edit(RailTransport train) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addElectricTrain.fxml"));
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
        stage.setTitle("Electric train");
        ElectricTrainController controller = loader.getController();
        controller.setElectricTrain((ElectricTrain) train);
        stage.showAndWait();
    }
}

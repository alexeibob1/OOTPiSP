package com.lab2.factories;

import com.lab2.controllers.DieselTrainController;
import com.lab2.controllers.ElectricTrainController;
import com.lab2.trains.DieselTrain;
import com.lab2.trains.ElectricTrain;
import com.lab2.trains.RailTransport;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DieselTrainFactory implements AbstractTrainFactory {
    @Override
    public DieselTrain add() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addDieselTrain.fxml"));
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
        stage.setTitle("Diesel train");
        DieselTrainController controller = loader.getController();
        stage.showAndWait();
        return controller.getDieselTrain();
    }

    @Override
    public void edit(RailTransport train) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addDieselTrain.fxml"));
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
        stage.setTitle("Diesel train");
        DieselTrainController controller = loader.getController();
        controller.setDieselTrain((DieselTrain) train);
        stage.showAndWait();
    }
}

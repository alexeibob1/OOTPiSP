package com.lab2.factories;

import com.lab2.controllers.SubwayController;
import com.lab2.controllers.TramController;
import com.lab2.trains.ElectricTrain;
import com.lab2.trains.RailTransport;
import com.lab2.trains.Subway;
import com.lab2.trains.Tram;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SubwayFactory implements AbstractTrainFactory {
    @Override
    public Subway add() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addSubway.fxml"));
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
        stage.setTitle("Subway");
        SubwayController controller = loader.getController();
        stage.showAndWait();
        return controller.getSubway();
    }

    @Override
    public void edit(RailTransport train) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addSubway.fxml"));
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
        stage.setTitle("Subway");
        SubwayController controller = loader.getController();
        controller.setSubway((Subway) train);
        stage.showAndWait();
    }
}

package com.lab2.factories;

import com.lab2.controllers.ElectricTrainController;
import com.lab2.controllers.FormController;
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
    public RailTransport add(String formFileName, String formTitle) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(formFileName));
        Stage stage = new Stage();
        Object controller = FormController.setFormParameters(formTitle, loader, stage);
        stage.showAndWait();
        return ((RailTransportController)controller).getRailTransport();
    }

    @Override
    public void edit(RailTransport train, String formName, String formTitle) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(formName));
        Stage stage = new Stage();
        Object controller = FormController.setFormParameters(formTitle, loader, stage);
        ((RailTransportController)controller).setRailTransport(train);
        stage.showAndWait();
    }
}

package com.lab2.factories;

import com.lab2.controllers.DieselTrainController;
import com.lab2.controllers.ElectricTrainController;
import com.lab2.controllers.FormController;
import com.lab2.controllers.RailTransportController;
import com.lab2.trains.DieselTrain;
import com.lab2.trains.ElectricTrain;
import com.lab2.trains.RailTransport;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ElectricTrainFactory implements AbstractTrainFactory {
    @Override
    public ElectricTrain add(String formFileName, String formTitle) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(formFileName));
        Stage stage = new Stage();
        Object controller = FormController.setFormParameters(formTitle, loader, stage);
        stage.showAndWait();
        return ((ElectricTrainController)controller).getElectricTrain();
    }

    @Override
    public void edit(RailTransport train, String formName, String formTitle) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(formName));
        Stage stage = new Stage();
        Object controller = FormController.setFormParameters(formTitle, loader, stage);
        ((ElectricTrainController)controller).setElectricTrain((ElectricTrain) train);
        stage.showAndWait();
    }
}

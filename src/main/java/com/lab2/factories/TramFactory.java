package com.lab2.factories;

import com.lab2.controllers.DieselTrainController;
import com.lab2.controllers.ElectricTrainController;
import com.lab2.controllers.FormController;
import com.lab2.controllers.TramController;
import com.lab2.trains.DieselTrain;
import com.lab2.trains.ElectricTrain;
import com.lab2.trains.RailTransport;
import com.lab2.trains.Tram;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TramFactory implements AbstractTrainFactory {
    @Override
    public Tram add(String formFileName, String formTitle) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(formFileName));
        Stage stage = new Stage();
        Object controller = FormController.setFormParameters(formTitle, loader, stage);
        stage.showAndWait();
        return ((TramController)controller).getTram();
    }

    @Override
    public void edit(RailTransport train, String formName, String formTitle) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(formName));
        Stage stage = new Stage();
        Object controller = FormController.setFormParameters(formTitle, loader, stage);
        ((TramController)controller).setTram((Tram) train);
        stage.showAndWait();
    }
}
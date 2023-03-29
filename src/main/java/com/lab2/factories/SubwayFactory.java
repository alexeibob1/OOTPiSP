package com.lab2.factories;

import com.lab2.controllers.DieselTrainController;
import com.lab2.controllers.FormController;
import com.lab2.controllers.SubwayController;
import com.lab2.controllers.TramController;
import com.lab2.trains.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SubwayFactory implements AbstractTrainFactory {
    @Override
    public Subway add(String formFileName, String formTitle) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(formFileName));
        Stage stage = new Stage();
        Object controller = FormController.setFormParameters(formTitle, loader, stage);
        stage.showAndWait();
        return ((SubwayController)controller).getSubway();
    }

    @Override
    public void edit(RailTransport train, String formName, String formTitle) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(formName));
        Stage stage = new Stage();
        Object controller = FormController.setFormParameters(formTitle, loader, stage);
        ((SubwayController)controller).setSubway((Subway) train);
        stage.showAndWait();
    }
}

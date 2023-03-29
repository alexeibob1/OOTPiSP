package com.lab2.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FormController {
    public static Object setFormParameters(String formTitle, FXMLLoader loader, Stage stage) {
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        Parent root = loader.getRoot();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setTitle(formTitle);
        return loader.getController();
    }
}

package com.lab2;

import javafx.scene.control.Alert;

public class ErrorWindow {
    public void showError() {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Incorrect input!");
        a.setHeaderText("Error");
        a.setContentText("Please, check input data and try again!");
        a.showAndWait();
    }
}

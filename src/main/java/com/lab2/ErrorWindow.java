package com.lab2;

import javafx.scene.control.Alert;

public class ErrorWindow {
    public void showError(Alert.AlertType type, String title, String header, String content) {
        Alert a = new Alert(type);
        a.setTitle(title);
        a.setHeaderText(header);
        a.setContentText(content);
        a.showAndWait();
    }
}

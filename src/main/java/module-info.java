module com.lab2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.lab2 to javafx.fxml;
    exports com.lab2;

    opens com.lab2.controllers to javafx.fxml;
    exports com.lab2.controllers;

    exports com.lab2.factories;
    exports com.lab2.trains;
}
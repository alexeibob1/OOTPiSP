module com.lab2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.slf4j;
    requires com.google.gson;
    requires java.base;
    requires gson.extras;
    requires org.apache.commons.codec;

    opens com.lab2 to javafx.fxml;
    exports com.lab2;

    opens com.lab2.controllers to javafx.fxml;
    exports com.lab2.controllers;

    exports com.lab2.factories;
    exports com.lab2.trains;

    opens com.lab2.trains to com.google.gson;
}
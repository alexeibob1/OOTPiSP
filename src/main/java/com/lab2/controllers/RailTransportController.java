package com.lab2.controllers;

import com.lab2.ErrorWindow;
import com.lab2.trains.Driver;
import com.lab2.trains.RailTransport;
import com.lab2.trains.Schedule;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import com.lab2.DataVerifier;

public class RailTransportController {
    public RailTransport railTransport;

    @FXML
    private Button btnConfirm;

    @FXML
    private DatePicker dpDepartureDate;

    @FXML
    private DatePicker dpDestinationDate;

    @FXML
    private TextField eDeparture;

    @FXML
    private TextField eDestination;

    @FXML
    private TextField eDriverExperience;

    @FXML
    private TextField eDriverID;

    @FXML
    private TextField eDriverName;

    @FXML
    private TextField eMaxSpeed;

    @FXML
    private TextField eTotalPlaces;

    @FXML
    private TextField eTrainID;

    @FXML
    private Label lDriverInfo;

    @FXML
    private Label lRailTransport;

    @FXML
    private Label lScheduleInfo;

    @FXML
    void btnConfirmClick(MouseEvent event) {
        if (isCorrectForm()) {
            if (railTransport != null) {
                railTransport.setDriver(new Driver(Integer.parseInt(eDriverExperience.getText()), Integer.parseInt(eDriverID.getText()), eDriverName.getText()));
                railTransport.setSchedule(new Schedule(eDeparture.getText(), eDestination.getText(), dpDepartureDate.getValue(), dpDestinationDate.getValue()));
                railTransport.setId(Integer.parseInt(eTrainID.getText()));
                railTransport.setMaxSpeed(Integer.parseInt(eMaxSpeed.getText()));
                railTransport.setTotalPlaces(Integer.parseInt(eTotalPlaces.getText()));
                railTransport.setInfoProperty(railTransport.toString());
            } else {
                railTransport = new RailTransport(Integer.parseInt(eTrainID.getText()), Integer.parseInt(eTotalPlaces.getText()),
                        Integer.parseInt(eMaxSpeed.getText()), new Schedule(eDeparture.getText(), eDestination.getText(),
                        dpDepartureDate.getValue(), dpDestinationDate.getValue()),
                        new Driver(Integer.parseInt(eDriverExperience.getText()), Integer.parseInt(eDriverID.getText()),
                                eDriverName.getText()));
            }
            Stage stage = (Stage) btnConfirm.getScene().getWindow();
            stage.close();
        } else {
            ErrorWindow error = new ErrorWindow();
            error.showError();
        }
    }

    public boolean isCorrectForm() {
        return (DataVerifier.isCorrectString(eDeparture.getText()) && DataVerifier.isCorrectString(eDestination.getText()) &&
            DataVerifier.isCorrectString(eDriverName.getText()) && DataVerifier.isCorrectInteger(eDriverExperience.getText()) &&
            DataVerifier.isCorrectInteger(eDriverID.getText()) && DataVerifier.isCorrectInteger(eTrainID.getText()) &&
            DataVerifier.isCorrectInteger(eMaxSpeed.getText()) && DataVerifier.isCorrectInteger(eTotalPlaces.getText())
            && DataVerifier.isCorrectDates(dpDepartureDate.getValue(), dpDestinationDate.getValue()));
    }

    public RailTransport getRailTransport() {
        return railTransport;
    }

    public void setRailTransport(RailTransport train) {
        this.railTransport = train;
        eDriverID.setText(String.valueOf(railTransport.getDriver().getId()));
        eDriverName.setText(railTransport.getDriver().getName());
        eDriverExperience.setText(String.valueOf(railTransport.getDriver().getExperience()));
        eDeparture.setText(railTransport.getSchedule().getDeparture());
        eDestination.setText(railTransport.getSchedule().getDestination());
        dpDepartureDate.setValue(railTransport.getSchedule().getDepartureDate());
        dpDestinationDate.setValue(railTransport.getSchedule().getDestinationDate());
        eMaxSpeed.setText(String.valueOf(railTransport.getMaxSpeed()));
        eTotalPlaces.setText(String.valueOf(railTransport.getTotalPlaces()));
        eTrainID.setText(String.valueOf(railTransport.getId()));
    }
}
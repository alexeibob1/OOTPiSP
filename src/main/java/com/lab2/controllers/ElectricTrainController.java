package com.lab2.controllers;

import com.lab2.DataVerifier;
import com.lab2.ErrorWindow;
import com.lab2.trains.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ElectricTrainController {

    public ElectricTrain electricTrain;
    @FXML
    private Button btnConfirm;

    @FXML
    private ComboBox<PowerSupply> cbPowerSupply;

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
    private TextField eVoltage;

    @FXML
    private Label lDriverInfo;

    @FXML
    private Label lElectricTrainInfo;

    @FXML
    private Label lScheduleInfo;

    @FXML
    void btnConfirmClick(MouseEvent event) {
        if (isCorrectForm()) {
            if (electricTrain != null) {
                electricTrain.setDriver(new Driver(Integer.parseInt(eDriverExperience.getText()), Integer.parseInt(eDriverID.getText()), eDriverName.getText()));
                electricTrain.setSchedule(new Schedule(eDeparture.getText(), eDestination.getText(), dpDepartureDate.getValue(), dpDestinationDate.getValue()));
                electricTrain.setId(Integer.parseInt(eTrainID.getText()));
                electricTrain.setMaxSpeed(Integer.parseInt(eMaxSpeed.getText()));
                electricTrain.setTotalPlaces(Integer.parseInt(eTotalPlaces.getText()));
                electricTrain.setVoltage(Integer.parseInt(eVoltage.getText()));
                electricTrain.setPowerSupply(cbPowerSupply.getValue());
                electricTrain.setInfoProperty(electricTrain.toString());
            } else {
                electricTrain = new ElectricTrain(Integer.parseInt(eTrainID.getText()), Integer.parseInt(eTotalPlaces.getText()),
                        Integer.parseInt(eMaxSpeed.getText()), new Schedule(eDeparture.getText(), eDestination.getText(),
                        dpDepartureDate.getValue(), dpDestinationDate.getValue()),
                        new Driver(Integer.parseInt(eDriverExperience.getText()), Integer.parseInt(eDriverID.getText()),
                                eDriverName.getText()), Integer.parseInt(eVoltage.getText()), cbPowerSupply.getValue());
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
                && DataVerifier.isCorrectDates(dpDepartureDate.getValue(), dpDestinationDate.getValue()) &&
                DataVerifier.isCorrectInteger(eVoltage.getText()));
    }

    public ElectricTrain getElectricTrain() {
        return this.electricTrain;
    }

    public void setElectricTrain(ElectricTrain train) {
        this.electricTrain = train;
        eDriverID.setText(String.valueOf(electricTrain.getDriver().getId()));
        eDriverName.setText(electricTrain.getDriver().getName());
        eDriverExperience.setText(String.valueOf(electricTrain.getDriver().getExperience()));
        eDeparture.setText(electricTrain.getSchedule().getDeparture());
        eDestination.setText(electricTrain.getSchedule().getDestination());
        dpDepartureDate.setValue(electricTrain.getSchedule().getDepartureDate());
        dpDestinationDate.setValue(electricTrain.getSchedule().getDestinationDate());
        eMaxSpeed.setText(String.valueOf(electricTrain.getMaxSpeed()));
        eTotalPlaces.setText(String.valueOf(electricTrain.getTotalPlaces()));
        eTrainID.setText(String.valueOf(electricTrain.getId()));
        eVoltage.setText(String.valueOf(electricTrain.getVoltage()));
        cbPowerSupply.getSelectionModel().select(electricTrain.getPowerSupply());
    }
    @FXML
    public void initialize() {
        cbPowerSupply.getItems().addAll(PowerSupply.AC, PowerSupply.DC, PowerSupply.IMP);
        cbPowerSupply.getSelectionModel().selectFirst();
    }
}
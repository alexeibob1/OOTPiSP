package com.lab2.controllers;

import com.lab2.DataVerifier;
import com.lab2.trains.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class DieselTrainController {

    public DieselTrain dieselTrain;
    @FXML
    private Button btnConfirm;

    @FXML
    private ComboBox<Fuel> cbFuel;

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
    private Label lDieselTrainInfo;

    @FXML
    private Label lDriverInfo;

    @FXML
    private Label lScheduleInfo;

    @FXML
    void btnConfirmClick(MouseEvent event) {
        if (isCorrectForm()) {
            if (dieselTrain != null) {
                dieselTrain.setDriver(new Driver(Integer.parseInt(eDriverExperience.getText()), Integer.parseInt(eDriverID.getText()), eDriverName.getText()));
                dieselTrain.setSchedule(new Schedule(eDeparture.getText(), eDestination.getText(), dpDepartureDate.getValue(), dpDestinationDate.getValue()));
                dieselTrain.setId(Integer.parseInt(eTrainID.getText()));
                dieselTrain.setMaxSpeed(Integer.parseInt(eMaxSpeed.getText()));
                dieselTrain.setTotalPlaces(Integer.parseInt(eTotalPlaces.getText()));
                dieselTrain.setFuel(cbFuel.getValue());
                dieselTrain.setInfoProperty(dieselTrain.toString());
            } else {
                dieselTrain = new DieselTrain(Integer.parseInt(eTrainID.getText()), Integer.parseInt(eTotalPlaces.getText()),
                        Integer.parseInt(eMaxSpeed.getText()), new Schedule(eDeparture.getText(), eDestination.getText(),
                        dpDepartureDate.getValue(), dpDestinationDate.getValue()),
                        new Driver(Integer.parseInt(eDriverExperience.getText()), Integer.parseInt(eDriverID.getText()),
                                eDriverName.getText()), cbFuel.getValue());
            }
            Stage stage = (Stage) btnConfirm.getScene().getWindow();
            stage.close();
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Incorrect input!");
            a.setHeaderText("Error");
            a.setContentText("Please, check input data and try again!");
            a.showAndWait();
        }
    }

    public boolean isCorrectForm() {
        return (DataVerifier.isCorrectString(eDeparture.getText()) && DataVerifier.isCorrectString(eDestination.getText()) &&
                DataVerifier.isCorrectString(eDriverName.getText()) && DataVerifier.isCorrectInteger(eDriverExperience.getText()) &&
                DataVerifier.isCorrectInteger(eDriverID.getText()) && DataVerifier.isCorrectInteger(eTrainID.getText()) &&
                DataVerifier.isCorrectInteger(eMaxSpeed.getText()) && DataVerifier.isCorrectInteger(eTotalPlaces.getText())
                && DataVerifier.isCorrectDates(dpDepartureDate.getValue(), dpDestinationDate.getValue()));
    }

    public DieselTrain getDieselTrain() {
        return this.dieselTrain;
    }
    public void setDieselTrain(DieselTrain train) {
        this.dieselTrain = train;
        eDriverID.setText(String.valueOf(dieselTrain.getDriver().getId()));
        eDriverName.setText(dieselTrain.getDriver().getName());
        eDriverExperience.setText(String.valueOf(dieselTrain.getDriver().getExperience()));
        eDeparture.setText(dieselTrain.getSchedule().getDeparture());
        eDestination.setText(dieselTrain.getSchedule().getDestination());
        dpDepartureDate.setValue(dieselTrain.getSchedule().getDepartureDate());
        dpDestinationDate.setValue(dieselTrain.getSchedule().getDestinationDate());
        eMaxSpeed.setText(String.valueOf(dieselTrain.getMaxSpeed()));
        eTotalPlaces.setText(String.valueOf(dieselTrain.getTotalPlaces()));
        eTrainID.setText(String.valueOf(dieselTrain.getId()));
        cbFuel.getSelectionModel().select(dieselTrain.getFuel());
    }
    @FXML
    public void initialize() {
        cbFuel.getItems().addAll(Fuel.DIESEL, Fuel.BIOMASS, Fuel.PETROLEUM, Fuel.CARBON);
        cbFuel.getSelectionModel().selectFirst();
    }

}

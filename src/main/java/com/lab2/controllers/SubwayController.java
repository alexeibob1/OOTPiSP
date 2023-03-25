package com.lab2.controllers;

import com.lab2.DataVerifier;
import com.lab2.trains.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SubwayController {

    Subway subway;
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
    private TextField eDepth;

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
    private Label lScheduleInfo;

    @FXML
    private Label lTramInfo;

    @FXML
    void btnConfirmClick(MouseEvent event) {
        if (isCorrectForm()) {
            if (subway != null) {
                subway.setDriver(new Driver(Integer.parseInt(eDriverExperience.getText()), Integer.parseInt(eDriverID.getText()), eDriverName.getText()));
                subway.setSchedule(new Schedule(eDeparture.getText(), eDestination.getText(), dpDepartureDate.getValue(), dpDestinationDate.getValue()));
                subway.setId(Integer.parseInt(eTrainID.getText()));
                subway.setMaxSpeed(Integer.parseInt(eMaxSpeed.getText()));
                subway.setTotalPlaces(Integer.parseInt(eTotalPlaces.getText()));
                subway.setVoltage(Integer.parseInt(eVoltage.getText()));
                subway.setPowerSupply(cbPowerSupply.getValue());
                subway.setDepth(Integer.parseInt(eDepth.getText()));
                subway.setInfoProperty(subway.toString());
            } else {
                subway = new Subway(Integer.parseInt(eTrainID.getText()), Integer.parseInt(eTotalPlaces.getText()),
                        Integer.parseInt(eMaxSpeed.getText()), new Schedule(eDeparture.getText(), eDestination.getText(),
                        dpDepartureDate.getValue(), dpDestinationDate.getValue()),
                        new Driver(Integer.parseInt(eDriverExperience.getText()), Integer.parseInt(eDriverID.getText()),
                                eDriverName.getText()), Integer.parseInt(eVoltage.getText()), cbPowerSupply.getValue(), Integer.parseInt(eDepth.getText()));
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
                && DataVerifier.isCorrectDates(dpDepartureDate.getValue(), dpDestinationDate.getValue()) &&
                DataVerifier.isCorrectInteger(eVoltage.getText()) && DataVerifier.isCorrectInteger(eDepth.getText()));
    }

    public Subway getSubway() {
        return this.subway;
    }
    public void setSubway(Subway train) {
        this.subway = train;
        eDriverID.setText(String.valueOf(subway.getDriver().getId()));
        eDriverName.setText(subway.getDriver().getName());
        eDriverExperience.setText(String.valueOf(subway.getDriver().getExperience()));
        eDeparture.setText(subway.getSchedule().getDeparture());
        eDestination.setText(subway.getSchedule().getDestination());
        dpDepartureDate.setValue(subway.getSchedule().getDepartureDate());
        dpDestinationDate.setValue(subway.getSchedule().getDestinationDate());
        eMaxSpeed.setText(String.valueOf(subway.getMaxSpeed()));
        eTotalPlaces.setText(String.valueOf(subway.getTotalPlaces()));
        eTrainID.setText(String.valueOf(subway.getId()));
        eVoltage.setText(String.valueOf(subway.getVoltage()));
        cbPowerSupply.getSelectionModel().select(subway.getPowerSupply());
        eDepth.setText(String.valueOf(subway.getDepth()));
    }
    @FXML
    public void initialize() {
        cbPowerSupply.getItems().addAll(PowerSupply.AC, PowerSupply.DC, PowerSupply.IMP);
        cbPowerSupply.getSelectionModel().selectFirst();
    }
}

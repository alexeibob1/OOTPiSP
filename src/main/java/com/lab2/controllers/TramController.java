package com.lab2.controllers;

import com.lab2.DataVerifier;
import com.lab2.trains.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class TramController {

    Tram tram;
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
    private TextField eNoisiness;

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
            if (tram != null) {
                tram.setDriver(new Driver(Integer.parseInt(eDriverExperience.getText()), Integer.parseInt(eDriverID.getText()), eDriverName.getText()));
                tram.setSchedule(new Schedule(eDeparture.getText(), eDestination.getText(), dpDepartureDate.getValue(), dpDestinationDate.getValue()));
                tram.setId(Integer.parseInt(eTrainID.getText()));
                tram.setMaxSpeed(Integer.parseInt(eMaxSpeed.getText()));
                tram.setTotalPlaces(Integer.parseInt(eTotalPlaces.getText()));
                tram.setVoltage(Integer.parseInt(eVoltage.getText()));
                tram.setPowerSupply(cbPowerSupply.getValue());
                tram.setNoisiness(Integer.parseInt(eNoisiness.getText()));
                tram.setInfoProperty(tram.toString());
            } else {
                tram = new Tram(Integer.parseInt(eTrainID.getText()), Integer.parseInt(eTotalPlaces.getText()),
                        Integer.parseInt(eMaxSpeed.getText()), new Schedule(eDeparture.getText(), eDestination.getText(),
                        dpDepartureDate.getValue(), dpDestinationDate.getValue()),
                        new Driver(Integer.parseInt(eDriverExperience.getText()), Integer.parseInt(eDriverID.getText()),
                                eDriverName.getText()), Integer.parseInt(eVoltage.getText()), cbPowerSupply.getValue(), Integer.parseInt(eNoisiness.getText()));
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
                DataVerifier.isCorrectInteger(eVoltage.getText()) && DataVerifier.isCorrectInteger(eNoisiness.getText()));
    }

    public Tram getTram() {
        return this.tram;
    }

    public void setTram(Tram train) {
        this.tram = train;
        eDriverID.setText(String.valueOf(tram.getDriver().getId()));
        eDriverName.setText(tram.getDriver().getName());
        eDriverExperience.setText(String.valueOf(tram.getDriver().getExperience()));
        eDeparture.setText(tram.getSchedule().getDeparture());
        eDestination.setText(tram.getSchedule().getDestination());
        dpDepartureDate.setValue(tram.getSchedule().getDepartureDate());
        dpDestinationDate.setValue(tram.getSchedule().getDestinationDate());
        eMaxSpeed.setText(String.valueOf(tram.getMaxSpeed()));
        eTotalPlaces.setText(String.valueOf(tram.getTotalPlaces()));
        eTrainID.setText(String.valueOf(tram.getId()));
        eVoltage.setText(String.valueOf(tram.getVoltage()));
        cbPowerSupply.getSelectionModel().select(tram.getPowerSupply());
        eNoisiness.setText(String.valueOf(tram.getNoisiness()));
    }
    @FXML
    public void initialize() {
        cbPowerSupply.getItems().addAll(PowerSupply.AC, PowerSupply.DC, PowerSupply.IMP);
        cbPowerSupply.getSelectionModel().selectFirst();
    }

}

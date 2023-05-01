package com.lab2;

import com.lab2.factories.*;
import com.lab2.serializers.*;
import com.lab2.trains.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;

public class MainWindowController {
    ObservableList<RailTransport> trainsList = FXCollections.observableArrayList();
    ObservableList<RailTransport> currTrains = FXCollections.observableArrayList();
    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnEdit;

    @FXML
    private ComboBox<TrainType> cbTrainTypes;

    @FXML
    private TableView<RailTransport> tableTrains;

    @FXML
    private TableColumn<RailTransport, Integer> tcTrainID;

    @FXML
    private TableColumn<RailTransport, String> tcTrainInfo;

    @FXML
    void btnAddClick(MouseEvent event) {
        if (cbTrainTypes.getSelectionModel().getSelectedItem() != null) {
            ConcreteTrainFactory factory = new ConcreteTrainFactory();
            TrainType trainInfo = cbTrainTypes.getSelectionModel().getSelectedItem();
            AbstractTrainFactory trainFactory = factory.getTrainFactory(trainInfo.getTrainType());
            RailTransport tempTrain = trainFactory.add(trainInfo.getFileName(), trainInfo.getTrainName());
            if (tempTrain != null) {
                trainsList.add(tempTrain);
                currTrains.add(tempTrain);
            }
        }
    }

    @FXML
    void btnEditClick(MouseEvent event) {
        if (tableTrains.getSelectionModel().getSelectedItem() != null) {
            ConcreteTrainFactory factory = new ConcreteTrainFactory();
            TrainType trainInfo = cbTrainTypes.getSelectionModel().getSelectedItem();
            AbstractTrainFactory trainFactory = factory.getTrainFactory(trainInfo.getTrainType());
            trainFactory.edit(tableTrains.getSelectionModel().getSelectedItem(), trainInfo.getFileName(), trainInfo.getTrainName());
            tableTrains.refresh();
        }
    }

    @FXML
    void btnDeleteClick(MouseEvent event) {
        if (tableTrains.getSelectionModel().getSelectedItem() != null) {
            trainsList.remove(tableTrains.getSelectionModel().getSelectedItem());
            currTrains.remove(tableTrains.getSelectionModel().getSelectedItem());
            tableTrains.refresh();
        }
    }

    @FXML
    void onBtnSaveToFileClicked(ActionEvent event) throws FileNotFoundException, InstantiationException, IllegalAccessException {
        if (trainsList.isEmpty()) {
            return;
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save to file...");
        SerializerFactory serializerFactory = new SerializerFactory();
        serializerFactory.setFilters(fileChooser);
        Stage currStage = (Stage)btnAdd.getScene().getWindow();
        File file = fileChooser.showSaveDialog(currStage);
        if (file != null) {
            String extension = file.getName().substring(file.getName().lastIndexOf('.') + 1);
            Serializable serializer = serializerFactory.getSerializerDescription(extension).getSerializer();
            serializer.serialize(trainsList, new FileOutputStream(file));
        }
    }

    @FXML
    void onBtnOpenFileClicked(ActionEvent event) throws InstantiationException, IllegalAccessException, FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file...");
        SerializerFactory serializerFactory = new SerializerFactory();
        serializerFactory.setFilters(fileChooser);
        Stage currStage = (Stage)btnAdd.getScene().getWindow();
        File file = fileChooser.showOpenDialog(currStage);
        if (file != null) {
            if (file.length() != 0) {
                String extension = file.getName().substring(file.getName().lastIndexOf('.') + 1);
                Serializable serializer = serializerFactory.getSerializerDescription(extension).getSerializer();
                ObservableList<RailTransport> deserializationList = serializer.deserialize(new FileInputStream(file));
                if (deserializationList.size() != 0) {
                    trainsList = deserializationList;
                    printSpecificTrains();
                }
            }
        }
    }

    private void printSpecificTrains() {
        Class<?> trainType = cbTrainTypes.getSelectionModel().getSelectedItem().getTrainType();
        currTrains.clear();
        for (RailTransport train : trainsList) {
            if (train.getClass().equals(trainType)) {
                currTrains.add(train);
            }
        }
    }

    @FXML
    void initialize() {
        cbTrainTypes.getItems().addAll(new TrainType(RailTransport.class, "Rail Transport", "addRailTransport.fxml"),
                new TrainType(ElectricTrain.class, "Electric Train", "addElectricTrain.fxml"),
                new TrainType(DieselTrain.class, "Diesel Train", "addDieselTrain.fxml"),
                new TrainType(Tram.class, "Tram", "addTram.fxml"),
                new TrainType(Subway.class, "Subway", "addSubway.fxml")
                );


        tcTrainID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcTrainInfo.setCellValueFactory(new PropertyValueFactory<>("trainInfo"));
        tableTrains.setItems(currTrains);
        cbTrainTypes.getSelectionModel().selectFirst();
        cbTrainTypes.valueProperty().addListener((observableValue, s, t1) -> printSpecificTrains());
        cbTrainTypes.setConverter(new StringConverter<TrainType>() {
            @Override
            public String toString(TrainType trainType) {
                return trainType.getTrainName();
            }

            @Override
            public TrainType fromString(String s) {
                return cbTrainTypes.getItems().stream().filter(trainType ->
                        trainType.getTrainName().equals(s)).findFirst().orElse(null);
            }
        });


        //hard code some examples
//        trainsList.addAll(
//                new RailTransport(372, 650, 150, new Schedule("Molodechno", "Minsk",
//                        LocalDate.parse("2023-02-01"), LocalDate.parse("2023-02-01")), new Driver(12, 627, "Alexei")),
//                new RailTransport(351, 670, 140, new Schedule("Minsk", "Borisov",
//                        LocalDate.parse("2023-04-02"), LocalDate.parse("2023-04-03")), new Driver(5, 425, "Yakov")),
//                new ElectricTrain(524, 635, 230, new Schedule("Pekin", "Seul",
//                        LocalDate.parse("2023-05-02"), LocalDate.parse("2023-05-03")), new Driver(23, 5345, "Xiaomi"),
//                        540, PowerSupply.IMP),
//                new ElectricTrain(7457, 1200, 150, new Schedule("Paris", "Berlin",
//                        LocalDate.parse("2023-04-12"), LocalDate.parse("2023-04-13")), new Driver(8, 452, "Tomas"),
//                        320, PowerSupply.DC),
//                new DieselTrain(653, 5, 76, new Schedule("Gudagai", "Molodechno",
//                        LocalDate.parse("2023-04-01"), LocalDate.parse("2023-04-02")), new Driver(15, 636, "Petr"), Fuel.PETROLEUM),
//                new Tram(743, 85, 65, new Schedule("Yakuba Kolasa station", "Peramogi station",
//                        LocalDate.parse("2023-04-02"), LocalDate.parse("2023-04-02")), new Driver(4, 234, "Anna"),
//                        654, PowerSupply.AC, 54),
//                new Tram(245, 90, 85, new Schedule("Lenina station", "Yakuba Kolasa station",
//                        LocalDate.parse("2023-04-05"), LocalDate.parse("2023-04-05")), new Driver(7, 345, "Kirill"),
//                        242, PowerSupply.IMP, 76),
//                new Subway(24745, 700, 90, new Schedule("Institut Kulturi", "Akademia Navuk",
//                        LocalDate.parse("2023-04-06"), LocalDate.parse("2023-04-06")), new Driver(13, 654, "Vadim"),
//                        1020, PowerSupply.AC, 25),
//                new Subway(738, 750, 100, new Schedule("Malinovka", "Uruchie",
//                        LocalDate.parse("2023-04-07"), LocalDate.parse("2023-04-07")), new Driver(3, 939, "Eugen"),
//                        1030, PowerSupply.AC, 20)
//        );
        printSpecificTrains();
    }
}
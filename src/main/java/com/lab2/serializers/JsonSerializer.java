package com.lab2.serializers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.lab2.ErrorWindow;
import com.lab2.trains.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.typeadapters.*;
import javafx.scene.control.Alert;

class LocalDateAdapterSer implements com.google.gson.JsonSerializer<LocalDate> {

    public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(date.format(DateTimeFormatter.ISO_LOCAL_DATE)); // "yyyy-mm-dd"
    }
}

class LocalDateAdapterDeser implements com.google.gson.JsonDeserializer<LocalDate> {
    @Override
    public LocalDate deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return LocalDate.parse(jsonElement.getAsJsonPrimitive().getAsString());
    }
}

public class JsonSerializer implements Serializable {
    private final RuntimeTypeAdapterFactory<RailTransport> typeAdapterFactory = RuntimeTypeAdapterFactory.of(RailTransport.class, "type")
            .registerSubtype(RailTransport.class, "rail transport")
            .registerSubtype(ElectricTrain.class, "electric train")
            .registerSubtype(DieselTrain.class, "diesel train")
            .registerSubtype(Tram.class, "tram")
            .registerSubtype(Subway.class, "subway");
    @Override
    public void serialize(ObservableList<RailTransport> trains, OutputStream outputStream) {
        List<RailTransport> serializableTrainList = new ArrayList<>(trains);
        Gson gson = new Gson().newBuilder().registerTypeAdapterFactory(typeAdapterFactory).registerTypeAdapter(LocalDate.class, new LocalDateAdapterSer()).create();
        Type type = new TypeToken<ArrayList<RailTransport>>(){}.getType();
        FileOutputStream fileOutputStream = (FileOutputStream) outputStream;
        try (PrintWriter printWriter = new PrintWriter(fileOutputStream)) {
            printWriter.println(gson.toJson(serializableTrainList, type));
        } catch (Exception e) {
            ErrorWindow alert = new ErrorWindow();
            alert.showError(Alert.AlertType.ERROR, "Error!", "Error while JSON serialization", "During serialization to JSON an error occurred. Please, try again");
        }
    }

    @Override
    public ObservableList<RailTransport> deserialize(InputStream inputStream) {
        ArrayList<RailTransport> trains = null;
        ObservableList<RailTransport> res = FXCollections.observableArrayList();
        Gson gson = new Gson().newBuilder().registerTypeAdapterFactory(typeAdapterFactory).registerTypeAdapter(LocalDate.class, new LocalDateAdapterDeser()).create();
        Type type = new TypeToken<ArrayList<RailTransport>>(){}.getType();
        String json = "";
        FileInputStream fileInputStream = (FileInputStream) inputStream;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream))) {
            json = bufferedReader.readLine();
            trains = gson.fromJson(json, type);
            res.addAll(trains);
            for (RailTransport train : res) {
                train.setInfoProperty();
            }
        } catch (Exception e) {
            ErrorWindow alert = new ErrorWindow();
            alert.showError(Alert.AlertType.ERROR, "Error!", "Error while JSON deserialization", "During deserialization of JSON an error occurred. Please, try again");
        }
        return res;
    }
}

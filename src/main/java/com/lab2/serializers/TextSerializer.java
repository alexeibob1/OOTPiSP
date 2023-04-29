package com.lab2.serializers;

import com.lab2.ErrorWindow;
import com.lab2.trains.Driver;
import com.lab2.trains.RailTransport;
import com.lab2.trains.Schedule;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextSerializer implements Serializable {

    public void serializeObject(Object o, PrintWriter writer, boolean isInner) throws NoSuchFieldException, IllegalAccessException {
        Class<?> oClass = o.getClass();
        if (o instanceof RailTransport) {
            writer.println("TRAIN: " + oClass.getName());
        } else {
            writer.println("INNER: " + oClass.getName());
        }
        Class<?> parentClass = oClass.getSuperclass();
        List<Field> fields = new ArrayList<>(Arrays.asList(oClass.getDeclaredFields()));
        while (parentClass != null) {
            fields.addAll(new ArrayList<>(Arrays.asList(parentClass.getDeclaredFields())));
            parentClass = parentClass.getSuperclass();
        }
        removeTransientFields(fields);
        for (Field field : fields) {
            field.setAccessible(true);
            Class<?> fieldType = field.getType();
            String fieldName = field.getName();
            List<Field> objFields = new ArrayList<>(Arrays.asList(fieldType.getDeclaredFields()));
            if (objFields.size() != 0 && (fieldType == Schedule.class || fieldType == Driver.class) ) {
                serializeObject(field.get(o), writer, true);
            } else {
                //writer.println("TYPE: " + fieldType);
                writer.println("NAME: " + fieldName);
                writer.println("VALUE: " + field.get(o));
            }
        }
        if (isInner) {
            writer.println("END");
        }
    }
    @Override
    public void serialize(ObservableList<RailTransport> trains, OutputStream outputStream) {
        FileOutputStream fileOutputStream = (FileOutputStream) outputStream;
        try (PrintWriter writer = new PrintWriter(fileOutputStream)) {
            for (RailTransport train : trains) {
                serializeObject(train, writer, false);
                writer.println();
            }
        } catch (Exception e) {
            ErrorWindow alert = new ErrorWindow();
            alert.showError(Alert.AlertType.ERROR, "Error!", "Error while text serialization", "During serialization to txt an error occurred. Please, try again");
        }

    }


    public void removeTransientFields(List<Field> fields) {
        for (Field field : fields) {
            if (Modifier.isTransient(field.getModifiers())) {
                fields.remove(field);
                break;
            }
        }
    }
    @Override
    public ObservableList<RailTransport> deserialize(InputStream inputStream) {
        return null;
    }
}

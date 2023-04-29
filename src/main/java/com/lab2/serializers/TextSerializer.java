package com.lab2.serializers;

import com.lab2.ErrorWindow;
import com.lab2.trains.Driver;
import com.lab2.trains.RailTransport;
import com.lab2.trains.Schedule;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
            writer.print("FIN");
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
        int[][] transitions = {
                {0, 0, 0, 0, 0, 0, 0},
                {2, 0, 0, 0, 0, 0, 6},
                {0, 4, 3, 0, 1, 0, 0},
                {0, 0, 0, 2, 0, 0, 0},
                {0, 0, 5, 0, 0, 0, 2},
                {0, 0, 0, 4, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0}
        };
        int[] finalState = {0, 0, 0, 0, 0, 1, 0};
        ArrayList<RailTransport> trains = null;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line = "";
            int state = 1;
            int prevState;
            Object train = null;
            Object innerObject = null;
            String fieldName = "";
            String fieldValue = "";
            String innerClassName = "";
            String trainClassName = "";
            Class<?> trainClass = null;
            while ((line = reader.readLine()) != null) {
                TokenType token = defineTokenType(line);
                prevState = state;
                state = transitions[state][token.ordinal()];
                if (state == 2) {
                    if (prevState == 3) {
                        fieldValue = line.substring(line.lastIndexOf("VALUE:") + 6).trim();
                        String setMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                        Field field = trainClass.getDeclaredField(fieldName);
                        field.setAccessible(true);
                        Class<?> fieldType = field.getType();
                        switch (fieldType.getName()) {
                            case "int": {
                                int intVal = Integer.parseInt(fieldValue);
                                Method setMethod = trainClass.getMethod(setMethodName, int.class);
                                setMethod.invoke(train, intVal);
                            }
                        }
                    }
                    else if (prevState == 1) {
                        trainClassName = line.substring(line.lastIndexOf("TRAIN:") + 6).trim();
                        trainClass = Class.forName(trainClassName);
                        Constructor<?> constructor = trainClass.getConstructor();
                        train = constructor.newInstance();
                    }
                }
                else if (state == 3) {
                    fieldName = line.substring(line.lastIndexOf("NAME:") + 5).trim();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public TokenType defineTokenType(String line) {
        if (line.startsWith("TRAIN")) {
            return TokenType.TRAIN;
        } else if (line.startsWith("INNER")) {
            return TokenType.INNER;
        } else if (line.startsWith("NAME")) {
            return TokenType.NAME;
        } else if (line.startsWith("VALUE")) {
            return TokenType.VALUE;
        } else if (line.startsWith("END")) {
            return TokenType.END;
        } else if (line.equals("\n")) {
            return TokenType.LINE;
        }
        return TokenType.ERROR;
    }
}

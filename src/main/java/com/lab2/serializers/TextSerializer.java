package com.lab2.serializers;

import com.lab2.ErrorWindow;
import com.lab2.trains.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.Alert;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextSerializer implements Serializable {

    public void serializeObject(Object o, PrintWriter writer, boolean isInner) throws NoSuchFieldException, IllegalAccessException {
        Class<?> oClass = o.getClass();
        if (o instanceof RailTransport) {
            writer.println("TRAIN: " + oClass.getName());
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
                writer.println("INNER: " + fieldName);
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
            writer.print("END");
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
        ArrayList<RailTransport> trains = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line = "";
            int state = 1;
            int prevState;
            Object train = null;
            Object innerObject = null;
            String fieldName = "";
            String fieldValue = "";
            String innerFieldName = "";
            String trainClassName = "";
            Class<?> trainClass = null;
            Class<?> innerClass = null;
            while ((line = reader.readLine()) != null) {
                TokenType token = defineTokenType(line);
                prevState = state;
                state = transitions[state][token.ordinal()];
                if (state == 1) {
                    trains.add((RailTransport) train);
                }
                else if (state == 2) {
                    if (prevState == 3) {
                        fieldValue = line.substring(line.lastIndexOf("VALUE:") + 6).trim();
                        String setMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

                        Field field = null;
                        Class<?> parentClass = trainClass;
                        while (parentClass != null) {
                            try {
                                field = parentClass.getDeclaredField(fieldName);
                            } catch (Exception e) {
                            }
                            parentClass = parentClass.getSuperclass();
                        }


                        field.setAccessible(true);
                        String fieldTypeName = field.getType().getName().toLowerCase();
                        if (fieldTypeName.contains("int")) {
                            int intVal = Integer.parseInt(fieldValue);
                            Method setMethod = trainClass.getMethod(setMethodName, int.class);
                            setMethod.invoke(train, intVal);
                        }
                        else if (fieldTypeName.contains("string")) {
                            Method setMethod = trainClass.getMethod(setMethodName, String.class);
                            setMethod.invoke(train, fieldValue);
                        }
                        else if (fieldTypeName.contains("powersupply")) {
                            PowerSupply powerSupply = PowerSupply.valueOf(fieldValue);
                            Method setMethod = trainClass.getMethod(setMethodName, PowerSupply.class);
                            setMethod.invoke(train, powerSupply);
                        }
                        else if (fieldTypeName.contains("fuel")) {
                            Fuel fuel = Fuel.valueOf(fieldValue);
                            Method setMethod = trainClass.getMethod(setMethodName, Fuel.class);
                            setMethod.invoke(train, fuel);
                        }
                    }
                    else if (prevState == 1) {
                        trainClassName = line.substring(line.lastIndexOf("TRAIN:") + 6).trim();
                        trainClass = Class.forName(trainClassName);
                        Constructor<?> constructor = trainClass.getConstructor();
                        train = constructor.newInstance();
                    }
                    else if (prevState == 4) {
                        String setMethodName = "set" + innerFieldName.substring(0, 1).toUpperCase() + innerFieldName.substring(1);
                        Method setMethod = trainClass.getMethod(setMethodName, innerClass);
                        setMethod.invoke(train, innerObject);
                    }
                }
                else if (state == 3 || state == 5) {
                    fieldName = line.substring(line.lastIndexOf("NAME:") + 5).trim();
                }
                else if (state == 4) {
                    if (prevState == 2) {
                        innerFieldName = line.substring(line.lastIndexOf("INNER:") + 6).trim();
                        //Field field = trainClass.getField(innerFieldName);

                        Field field = null;
                        Class<?> parentClass = trainClass;
                        while (parentClass != null) {
                            try {
                                field = parentClass.getDeclaredField(innerFieldName);
                            } catch (Exception e) {
                            }
                            parentClass = parentClass.getSuperclass();
                        }

                        innerClass = field.getType();
                        Constructor<?> constructor = innerClass.getConstructor();
                        innerObject = constructor.newInstance();
                    } else if (prevState == 5) {
                        fieldValue = line.substring(line.lastIndexOf("VALUE:") + 6).trim();
                        String setMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                        Field field = innerClass.getDeclaredField(fieldName);
                        field.setAccessible(true);
                        Class<?> fieldType = field.getType();
                        String fieldTypeName = fieldType.getName().toLowerCase();
                        if (fieldTypeName.contains("int")) {
                            int intVal = Integer.parseInt(fieldValue);
                            Method setMethod = innerClass.getMethod(setMethodName, int.class);
                            setMethod.invoke(innerObject, intVal);
                        } else if (fieldTypeName.contains("string")) {
                            Method setMethod = innerClass.getMethod(setMethodName, String.class);
                            setMethod.invoke(innerObject, fieldValue);
                        } else if (fieldTypeName.contains("localdate")) {
                            Method setMethod = innerClass.getMethod(setMethodName, LocalDate.class);
                            LocalDate date = LocalDate.parse(fieldValue);
                            setMethod.invoke(innerObject, date);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (RailTransport train : trains) {
            train.setInfoProperty();
        }

        return FXCollections.observableArrayList(trains);
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
        } else if (line.equals("")) {
            return TokenType.LINE;
        }
        return TokenType.ERROR;
    }
}
package com.lab2.serializers;

import com.lab2.ErrorWindow;
import com.lab2.trains.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.io.*;
import java.lang.reflect.*;
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

    public Field getHierarchyField(Class<?> objClass, String fieldName) {
        Field field = null;
        Class<?> parentClass = objClass;
        while (parentClass != null) {
            try {
                field = parentClass.getDeclaredField(fieldName);
            } catch (Exception ignored) {
            }
            parentClass = parentClass.getSuperclass();
        }
        return field;
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
            while ((line = reader.readLine()) != null && state != 0) {
                TokenType token = defineTokenType(line);
                prevState = state;
                state = transitions[state][token.ordinal()];
                if (state == 1) {
                    trains.add((RailTransport) train);
                }
                else if (state == 2) {
                    if (prevState == 3) {
                        fieldValue = extractParam(line, "VALUE:");
                        Field field = getHierarchyField(trainClass, fieldName);
                        field.setAccessible(true);
                        invokeSetMethod(trainClass, field, train, fieldValue);
                    }
                    else if (prevState == 1) {
                        trainClassName = extractParam(line, "TRAIN:");
                        trainClass = Class.forName(trainClassName);
                        Constructor<?> constructor = trainClass.getConstructor();
                        train = constructor.newInstance();
                    }
                    else if (prevState == 4) {
                        String setMethodName = getSetMethodName(innerFieldName);
                        Method setMethod = trainClass.getMethod(setMethodName, innerClass);
                        setMethod.invoke(train, innerObject);
                    }
                }
                else if (state == 3 || state == 5) {
                    fieldName = extractParam(line, "NAME:");
                }
                else if (state == 4) {
                    if (prevState == 2) {
                        innerFieldName = extractParam(line, "INNER:");
                        Field field = getHierarchyField(trainClass, innerFieldName);
                        innerClass = field.getType();
                        Constructor<?> constructor = innerClass.getConstructor();
                        innerObject = constructor.newInstance();
                    } else if (prevState == 5) {
                        fieldValue = extractParam(line, "VALUE:");
                        Field field = getHierarchyField(innerClass, fieldName);
                        field.setAccessible(true);
                        invokeSetMethod(innerClass, field, innerObject, fieldValue);
                    }
                }
            }

            if (state == 0) {
                ErrorWindow alert = new ErrorWindow();
                alert.showError(Alert.AlertType.ERROR, "Error!", "Error while txt deserialization", "During deserialization of TXT an error occurred. Please, try again");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



        for (RailTransport train : trains) {
            train.setInfoProperty();
        }

        return FXCollections.observableArrayList(trains);
    }

    public String extractParam(String line, String paramName) {
        return line.substring(line.lastIndexOf(paramName) + paramName.length()).trim();
    }

    public void invokeSetMethod(Class<?> objClass, Field field, Object obj, String fieldValue) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String fieldName = field.getName();
        String fieldTypeName = field.getType().getName().toLowerCase();
        String setMethodName = getSetMethodName(fieldName);
        if (fieldTypeName.contains("int")) {
            int intVal = Integer.parseInt(fieldValue);
            Method setMethod = getSetter(setMethodName, int.class, objClass);
            setMethod.invoke(obj, intVal);
        }
        else if (fieldTypeName.contains("string")) {
            Method setMethod = getSetter(setMethodName, String.class, objClass);
            setMethod.invoke(obj, fieldValue);
        }
        else if (fieldTypeName.contains("powersupply")) {
            PowerSupply powerSupply = PowerSupply.valueOf(fieldValue);
            Method setMethod = getSetter(setMethodName, PowerSupply.class, objClass);
            setMethod.invoke(obj, powerSupply);
        }
        else if (fieldTypeName.contains("fuel")) {
            Fuel fuel = Fuel.valueOf(fieldValue);
            Method setMethod = getSetter(setMethodName, Fuel.class, objClass);
            setMethod.invoke(obj, fuel);
        }
        else if (fieldTypeName.contains("localdate")) {
            LocalDate date = LocalDate.parse(fieldValue);
            Method setMethod = getSetter(setMethodName, LocalDate.class, objClass);
            setMethod.invoke(obj, date);
        }
    }

    public String getSetMethodName(String fieldName) {
        return "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    public Method getSetter(String setterName, Class<?> paramClass, Class<?> objClass) throws NoSuchMethodException {
        return objClass.getMethod(setterName, paramClass);
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
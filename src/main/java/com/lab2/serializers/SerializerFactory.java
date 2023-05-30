package com.lab2.serializers;

import javafx.stage.FileChooser;

import java.util.HashMap;
import java.util.Map;

public class SerializerFactory {
    private Map<String, SerializerDescription> serializers = new HashMap<>();

    public SerializerFactory() {
        serializers.put("txt", new SerializerDescription(TextSerializer.class, "Text document", "txt"));
        serializers.put("bin", new SerializerDescription(BinarySerializer.class, "Binary file", "bin"));
        serializers.put("json", new SerializerDescription(JsonSerializer.class, "JSON file", "json"));
    }

    public void setFilters(FileChooser fileChooser) {
        for (String extension : serializers.keySet()) {
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(serializers.get(extension).toString(), "*." + extension, "*." + extension + ".base32", "*." + extension + ".base64"));
        }
    }

    public SerializerDescription getSerializerDescription(String extension) {
        return serializers.get(extension);
    }
}

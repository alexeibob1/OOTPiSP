package com.lab2.serializers;

public class SerializerDescription {
    private Class<? extends Serializable> serializer;
    private final String name;
    private final String extension;

    public SerializerDescription(Class<? extends Serializable> serializer, String name, String extension) {
        this.serializer = serializer;
        this.name = name;
        this.extension = extension;
    }

    public Serializable getSerializer() throws InstantiationException, IllegalAccessException {
        return this.serializer.newInstance();
    }

    public String getExtension() {
        return extension;
    }

    @Override
    public String toString() {
        return name;
    }
}

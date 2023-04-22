package com.lab2.serializers;

public class SerializerDescription {
    private Class<? extends Serializable> serializer;
    private String name;
    private String extension;

    public SerializerDescription(Class<? extends Serializable> serializer, String name, String extension) {
        this.serializer = serializer;
        this.name = name;
        this.extension = extension;
    }

    public Class<? extends Serializable> getSerializer() {
        return serializer;
    }

    public String getExtension() {
        return extension;
    }

    @Override
    public String toString() {
        return name;
    }
}

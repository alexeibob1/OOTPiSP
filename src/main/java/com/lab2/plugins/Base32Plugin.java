package com.lab2.plugins;


import org.apache.commons.codec.binary.Base32;

public class Base32Plugin implements Plugin {
    @Override
    public String getExtension() {
        return "base32";
    }

    @Override
    public String getName() {
        return "Base 32";
    }

    @Override
    public byte[] encode(byte[] data) {
        return (new Base32()).encode(data);
    }

    @Override
    public byte[] decode(byte[] data) {
        return (new Base32()).decode(data);
    }
}

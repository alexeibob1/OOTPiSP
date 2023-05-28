package com.lab2.plugins;


import org.apache.commons.codec.binary.Base64;

public class Base64Plugin implements Plugin {
    @Override
    public String getExtension() {
        return "base64";
    }

    @Override
    public String getName() {
        return "Base 64";
    }

    @Override
    public byte[] encode(byte[] data) {
        return Base64.encodeBase64(data);
    }

    @Override
    public byte[] decode(byte[] data) {
        return Base64.decodeBase64(data);
    }
}

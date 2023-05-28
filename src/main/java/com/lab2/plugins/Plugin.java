package com.lab2.plugins;

public interface Plugin {
    String getExtension();
    String getName();
    byte[] encode(byte[] data);
    byte[] decode(byte[] data);
}

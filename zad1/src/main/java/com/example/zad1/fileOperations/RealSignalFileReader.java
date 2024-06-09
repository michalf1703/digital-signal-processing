package com.example.zad1.fileOperations;

import com.example.zad1.Signals.Signal;

import java.io.*;

public class RealSignalFileReader {
    private String filename;

    public RealSignalFileReader(String filename) {
        this.filename = filename;
    }

    public Signal read() throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (Signal) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException("Class not found", e);
        }
    }
}
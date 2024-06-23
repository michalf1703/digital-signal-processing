package com.example.zad1.fileOperations;

import com.example.zad1.Task4.Data.ComplexSignal;

import java.io.*;

public class ComplexSignalFileReader {
    private String filename;

    public ComplexSignalFileReader(String filename) {
        this.filename = filename;
    }

    public ComplexSignal read() throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (ComplexSignal) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException("Class not found", e);
        }
    }
}
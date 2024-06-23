package com.example.zad1.fileOperations;

import com.example.zad1.Task4.Data.ComplexSignal;

import java.io.*;

public class ComplexSignalFileWriter {
    private String filename;

    public ComplexSignalFileWriter(String filename) {
        this.filename = filename;
    }

    public void write(ComplexSignal signal) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(signal);
        }
    }
}
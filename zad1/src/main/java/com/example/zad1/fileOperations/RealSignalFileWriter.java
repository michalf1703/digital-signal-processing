package com.example.zad1.fileOperations;

import com.example.zad1.Signals.Signal;

import java.io.*;

public class RealSignalFileWriter {
    private String filename;

    public RealSignalFileWriter(String filename) {
        this.filename = filename;
    }

    public void write(Signal signal) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(signal);
        }
    }
}
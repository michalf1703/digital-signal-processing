package com.example.zad1;

import java.io.*;

public class FileReader<T> {

    private String filename;

    public FileReader(String filename) {
        this.filename = filename;
    }

    public T read() throws FileOperationException {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename))) {
            return (T) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new FileOperationException(e);
        }
    }

    public void write(T object) throws FileOperationException {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(filename))) {
            output.writeObject(object);
        } catch (IOException e) {
            throw new FileOperationException(e);
        }
    }
}
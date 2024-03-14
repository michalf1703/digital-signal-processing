package com.example.zad1;

import java.io.IOException;

public class FileOperationException extends IOException {

    public FileOperationException() {
    }

    public FileOperationException(String message) {
        super(message);
    }

    public FileOperationException(Throwable cause) {
        super(cause);
    }
}



package com.example.zad1.Signals;

public enum OperationType {

    ADDITION("dodawanie"),
    SUBTRACTION("odejmowanie"),
    MULTIPLICATION("mnożenie"),
    DIVISION("dzielenie");

    private final String name;

    OperationType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

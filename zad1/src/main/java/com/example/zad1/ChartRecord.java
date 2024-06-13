package com.example.zad1;

public class ChartRecord<X, Y> {
    private X x;
    private Y y;

    public ChartRecord(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    public X getX() {
        return x;
    }

    public Y getY() {
        return y;
    }
}
package com.example.zad1.window;

public class Hanning implements Window, java.io.Serializable{

    private final int M;

    public Hanning(int M) {
        this.M = M;
    }

    @Override
    public double w(int n) {
        return 0.5 - 0.5 * Math.cos(2.0 * Math.PI * n / M);
    }
}

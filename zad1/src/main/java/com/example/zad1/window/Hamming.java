package com.example.zad1.window;

public class Hamming implements Window, java.io.Serializable{
    private final int M;

    public Hamming(int M) {
        this.M = M;
    }

    @Override
    public double w(int n) {
        return 0.53836 - 0.46164 * Math.cos(2.0 * Math.PI * n / M);
    }
}

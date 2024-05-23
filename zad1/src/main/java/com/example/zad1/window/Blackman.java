package com.example.zad1.window;

public class Blackman implements Window{
    private final int M;

    public Blackman(int M) {
        this.M = M;
    }

    @Override
    public double w(int n) {
        return 0.42 - 0.5 * Math.cos(2.0 * Math.PI * n / M) + 0.08 * Math.cos(4.0 * Math.PI * n / M);
    }
}

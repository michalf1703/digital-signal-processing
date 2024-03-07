package com.example.zad1;

public abstract class Signal {
    protected double amplitude;

    public Signal(double amplitude) {
        this.amplitude = amplitude;
    }

    public abstract double[] generateData();
}
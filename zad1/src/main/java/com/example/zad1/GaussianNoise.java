package com.example.zad1;

public class GaussianNoise extends ContinuousSignal {
    private final double mean;
    private final double standardDeviation;

    public GaussianNoise(double amplitude, double startTime, double duration) {
        super(amplitude, startTime, duration);
        this.mean = 0.0;
        this.standardDeviation = 1.0;
    }
}

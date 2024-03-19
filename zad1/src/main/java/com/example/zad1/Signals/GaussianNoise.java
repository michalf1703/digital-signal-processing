package com.example.zad1.Signals;

import java.util.Random;

public class GaussianNoise extends ContinuousSignal {

    private final double amplitude;
    private final Random rand;


    public GaussianNoise(double rangeStart, double rangeLength, double amplitude, double sampleRate) {
        super(rangeStart, rangeLength,sampleRate);
        this.amplitude = amplitude;
        this.rand = new Random();
    }

    @Override
    protected double value(double t) {
        double u, v, s;
        do {
            u = 2.0 * rand.nextDouble() - 1.0;
            v = 2.0 * rand.nextDouble() - 1.0;
            s = u * u + v * v;
        } while (s >= 1.0 || s == 0.0);
        double standardDeviation = 1;
        double mean = 0;
        double multiplier = Math.sqrt(-2.0 * Math.log(s) / s);
        double gaussianValue = u * multiplier;
        return mean + standardDeviation * gaussianValue;
    }

    @Override
    public double getSampleRate() {
        return this.sampleRate;
    }

    @Override
    public String getName() {
        return "szum gaussowski";
    }
}

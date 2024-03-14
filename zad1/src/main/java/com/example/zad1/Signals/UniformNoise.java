package com.example.zad1.Signals;

import java.util.Random;

public class UniformNoise extends ContinuousSignal {

    private final double amplitude;
    private final Random rand;

    public UniformNoise(double rangeStart, double rangeLength, double amplitude, double sampleRate) {
        super(rangeStart, rangeLength, sampleRate);
        this.amplitude = amplitude;
        this.rand = new Random();
    }

    @Override
    protected double value(double t) {
        return (rand.nextDouble() * 2.0 - 1.0) * amplitude;
    }

    @Override
    public String getName() {
        return "szum jednostajny";
    }
}


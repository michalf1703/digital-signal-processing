package com.example.zad1.Signals;

import java.util.Random;

public class ImpulseNoise extends DiscreteSignal {

    private final double amplitude;
    private final double probability;
    private final Random rand;

    public ImpulseNoise(double rangeStart, double rangeLength, double sampleRate,
                        double amplitude, double probability) {
        super(rangeStart, rangeLength, sampleRate);
        this.amplitude = amplitude;
        this.probability = probability;
        this.rand = new Random();
    }

    @Override
    protected double value(double t) {
        if (rand.nextDouble() < probability) {
            return amplitude;
        } else {
            return 0.0;
        }
    }

    @Override
    public String getName() {
        return "szum impulsowy";
    }
}

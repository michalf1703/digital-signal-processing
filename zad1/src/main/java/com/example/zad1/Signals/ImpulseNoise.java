package com.example.zad1.Signals;

import java.util.Random;

public class ImpulseNoise extends DiscreteSignal {

    private final double amplitude;
    private final double probability;
    private final Random rand;

    public ImpulseNoise(double rangeStart, double rangeLength, double sampleRate,
                        double amplitude, double probability) {
        super(rangeStart, rangeLength, sampleRate, null);
        this.amplitude = amplitude;
        this.probability = probability;
        this.rand = new Random();
    }

    @Override
    public double value(int t) {
        if (rand.nextDouble() < probability) {
            return amplitude;
        } else {
            return 0.0;
        }
    }
    @Override
    public double getSampleRate() {
        return this.sampleRate;
    }
    @Override
    public String getName() {
        return "szum impulsowy";
    }
}

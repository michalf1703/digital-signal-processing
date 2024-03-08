package com.example.zad1.Signals;

public class UnitImpulse extends DiscreteSignal {
    private final double amplitude;
    private final int jumpSampleNumber;

    public UnitImpulse(double rangeStart, double rangeLength, double sampleRate,
                             double amplitude, int jumpSampleNumber) {
        super(rangeStart, rangeLength, sampleRate);
        this.amplitude = amplitude;
        this.jumpSampleNumber = jumpSampleNumber;
    }

    @Override
    protected double value(double t) {
        double step = rangeLength / (data.length - 1);
        if (t == jumpSampleNumber * step) {
            return amplitude;
        } else {
            return 0.0;
        }
    }
}

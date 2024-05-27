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
    public double value(int i) {
        if (i == jumpSampleNumber) {
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
        return "impuls jednostkowy";
    }
}

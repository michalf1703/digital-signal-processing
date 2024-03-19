package com.example.zad1.Signals;

public class UnitStep extends ContinuousSignal{
    private final double amplitude;
    private final double jumpPosition;

    public UnitStep(double rangeStart, double rangeLength, double amplitude,
                          double jumpPosition, double sampleRate) {
        super(rangeStart, rangeLength, sampleRate);
        this.amplitude = amplitude;
        this.jumpPosition = jumpPosition;
    }

    @Override
    protected double value(double t) {
        if (t > jumpPosition) {
            return amplitude;
        } else if (t == jumpPosition) {
            return 0.5 * amplitude;
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
        return "skok jednostkowy";
    }
}
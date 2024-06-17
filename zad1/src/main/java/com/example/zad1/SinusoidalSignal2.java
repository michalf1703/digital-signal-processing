package com.example.zad1;

import com.example.zad1.Signals.ContinuousSignal;

public class SinusoidalSignal2 extends ContinuousSignal {

    private final double amplitude;
    private final double frequency;
    private final double phaseShift;
    private final double sampleRate;

    public SinusoidalSignal2(double rangeStart, double rangeLength, double amplitude, double frequency, double phaseShift, double sampleRate) {
        super(rangeStart, rangeLength, sampleRate);
        this.amplitude = amplitude;
        this.frequency = frequency;
        this.phaseShift = phaseShift;
        this.sampleRate = sampleRate;
    }

    @Override
    public double value(double t) {
        return amplitude * Math.sin(2.0 * Math.PI * frequency * t + phaseShift);
    }

    @Override
    public double getSampleRate() {
        return this.sampleRate;
    }

    @Override
    public String getName() {
        return "sygnał sinusoidalny";
    }

    @Override
    public int getNumberOfSamples() {
        return (int) (getRangeLength() * getSampleRate());
    }
}

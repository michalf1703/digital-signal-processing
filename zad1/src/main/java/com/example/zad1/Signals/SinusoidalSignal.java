package com.example.zad1.Signals;

public class SinusoidalSignal extends ContinuousSignal {

    private final double amplitude;
    private final double term;
    private final double sampleRate;

    public SinusoidalSignal(double rangeStart, double rangeLength, double amplitude, double term, double sampleRate) {
        super(rangeStart, rangeLength,sampleRate);
        this.amplitude = amplitude;
        this.term = term;
        this.sampleRate = sampleRate;
    }

    @Override
    public double value(double t) {
        return amplitude * Math.sin((2.0 * Math.PI / term) * (t - rangeStart));
    }
    @Override
    public double getSampleRate() {
        return this.sampleRate;
    }
    @Override
    public String getName() {
        return "sygnał sinusoidalny";
    }
}

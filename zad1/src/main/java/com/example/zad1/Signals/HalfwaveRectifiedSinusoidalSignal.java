package com.example.zad1.Signals;

public class HalfwaveRectifiedSinusoidalSignal extends ContinuousSignal {
    private final double amplitude;
    private final double term;

    public HalfwaveRectifiedSinusoidalSignal(double rangeStart, double rangeLength,
                                            double amplitude, double term, double sampleRate) {
        super(rangeStart, rangeLength, sampleRate);
        this.amplitude = amplitude;
        this.term = term;
    }

    @Override
    public double value(double t) {
        return 0.5 * amplitude
                * (Math.sin((2.0 * Math.PI / term) * (t - rangeStart))
                + Math.abs(Math.sin((2.0 * Math.PI / term) * (t - rangeStart))));
    }
    @Override
    public double getSampleRate() {
        return this.sampleRate;
    }
    @Override
    public String getName() {
        return "sygnał sinusoidalny wyprostowany jednopołówkowo";
    }
}

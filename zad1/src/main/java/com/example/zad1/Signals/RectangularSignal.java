package com.example.zad1.Signals;

public class RectangularSignal extends ContinuousSignal {

    private final double amplitude;
    private final double term;
    private final double fulfillment;

    public RectangularSignal(double rangeStart, double rangeLength,
                             double amplitude, double term, double fulfillment, double sampleRate) {
        super(rangeStart, rangeLength, sampleRate);
        this.amplitude = amplitude;
        this.term = term;
        this.fulfillment = fulfillment;
    }

    @Override
    protected double value(double t) {
        double k = Math.floor((t - rangeStart) / term);
        double startTime = k * term + rangeStart;
        double endTime = startTime + fulfillment * term;

        if (t >= startTime && t <= endTime) {
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
        return "sygnał prostokątny";
    }
}
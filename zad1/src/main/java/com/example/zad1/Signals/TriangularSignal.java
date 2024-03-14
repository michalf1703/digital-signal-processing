package com.example.zad1.Signals;

public class TriangularSignal extends ContinuousSignal {

    private final double amplitude;
    private final double term;
    private final double fulfillment;

    public TriangularSignal(double rangeStart, double rangeLength,
                            double amplitude, double term, double fulfillment, double sampleRate) {
        super(rangeStart, rangeLength, sampleRate);
        this.amplitude = amplitude;
        this.term = term;
        this.fulfillment = fulfillment;
    }

    @Override
    protected double value(double t) {
        double termPosition = ((t - rangeStart) / term) - Math.floor((t - rangeStart) / term);
        if (termPosition < fulfillment) {
            return termPosition / fulfillment * amplitude;
        } else {
            return (1 - (termPosition - fulfillment) / (1 - fulfillment)) * amplitude;
        }
    }

    @Override
    public String getName() {
        return "sygnał trójkątny";
    }
}

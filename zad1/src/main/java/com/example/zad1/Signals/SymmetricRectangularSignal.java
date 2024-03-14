package com.example.zad1.Signals;

public class SymmetricRectangularSignal extends ContinuousSignal {

    private final double amplitude;
    private final double term;
    private final double fulfillment;

    public SymmetricRectangularSignal (double rangeStart, double rangeLength, double amplitude,
                                      double term, double fulfillment) {
        super(rangeStart, rangeLength);
        this.amplitude = amplitude;
        this.term = term;
        this.fulfillment = fulfillment;
    }

    @Override
    protected double value(double t) {
        if (((t - rangeStart) / term) - Math.floor((t - rangeStart) / term) < fulfillment) {
            return amplitude;
        } else {
            return -amplitude;
        }
    }

    @Override
    public String getName() {
        return "sygnał prostokątny symetryczny";
    }
}
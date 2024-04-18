package com.example.zad1.Signals;

public class SymmetricRectangularSignal extends ContinuousSignal {

    private final double amplitude;
    private final double term;
    private final double fulfillment;

    public SymmetricRectangularSignal (double rangeStart, double rangeLength, double amplitude,
                                      double term, double fulfillment, double sampleRate) {
        super(rangeStart, rangeLength, sampleRate);
        this.amplitude = amplitude;
        this.term = term;
        this.fulfillment = fulfillment;
    }

    @Override
    public double value(double t) {
        double periodOffset = (t - rangeStart) / term;
        double k = Math.floor(periodOffset);
        double kTPlusT1 = k * term + rangeStart;

        if (t >= kTPlusT1 && t <= kTPlusT1 + fulfillment) {
            return amplitude;
        } else {
            return -amplitude;
        }
    }
    @Override
    public double getSampleRate() {
        return this.sampleRate;
    }
    @Override
    public String getName() {
        return "sygnał prostokątny symetryczny";
    }
}
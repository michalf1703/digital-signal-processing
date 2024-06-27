package com.example.zad1.Signals;

public class ContinuousBasedDiscreteSignal extends DiscreteSignal{

    private ContinuousSignal continuousSignal;

    public ContinuousBasedDiscreteSignal(double rangeStart, double rangeLength,
                                         double sampleRate, ContinuousSignal continuousSignal) {
        super(rangeStart, rangeLength, sampleRate);
        this.continuousSignal = continuousSignal;
    }

    @Override
    public double value(int i) {
        return continuousSignal.value(argument(i));
    }

    @Override
    public String getName() {
        return null;
    }
}
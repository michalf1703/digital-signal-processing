package com.example.zad1.RadarSignals;


import com.example.zad1.Signals.ContinuousSignal;

public class ContinuousBasedDiscreteSignal2 extends DiscreteSignal2 {

    private ContinuousSignal continuousSignal;

    public ContinuousBasedDiscreteSignal2(double rangeStart, double rangeLength,
                                         double sampleRate, ContinuousSignal continuousSignal) {
        super(rangeStart, rangeLength, sampleRate);
        this.continuousSignal = continuousSignal;
    }

    public ContinuousBasedDiscreteSignal2(double rangeStart, int numberOfSamples,
                                         double sampleRate, ContinuousSignal continuousSignal) {
        super(rangeStart, numberOfSamples, sampleRate);
        this.continuousSignal = continuousSignal;
    }

    @Override
    public double value(int i) {
        return continuousSignal.value(argument(i));
    }
}

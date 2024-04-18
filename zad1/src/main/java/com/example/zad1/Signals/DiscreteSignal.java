
package com.example.zad1.Signals;


import com.example.zad1.Base.Data;

import java.util.ArrayList;
import java.util.List;

public abstract class DiscreteSignal extends Signal {

    public final double sampleRate;
    private final int numberOfSamples;
    private final double step;
    private ContinuousSignal continuousSignal;

    public DiscreteSignal(double rangeStart, double rangeLength, double sampleRate, ContinuousSignal continuousSignal) {
        super(rangeStart, rangeLength,sampleRate);
        this.sampleRate = sampleRate;
        this.numberOfSamples = (int) (rangeLength * sampleRate);
        this.step = 1.0 / sampleRate;
        this.continuousSignal = continuousSignal;
    }


    public double getSampleRate() {
        return sampleRate;
    }

    public int getNumberOfSamples() {
        return numberOfSamples;
    }

    public double value(int i) {
        return continuousSignal.value(argument(i));
    }

    public double argument(int i) {
        return i * step + getRangeStart();
    }

    @Override
    public List<Data> generateDiscreteRepresentation() {
        List<Data> data = new ArrayList<>();
        for (int i = 0; i < numberOfSamples; i++) {
            data.add(new Data(argument(i), value(i)));
        }
        return data;
    }

    public abstract String getName();
}

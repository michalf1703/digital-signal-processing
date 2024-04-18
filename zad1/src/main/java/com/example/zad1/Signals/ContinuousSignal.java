package com.example.zad1.Signals;


import com.example.zad1.Base.Data;

import java.util.List;

public abstract class ContinuousSignal extends Signal {

    public ContinuousSignal(double rangeStart, double rangeLength,double sampleRate){
        super(rangeStart, rangeLength, sampleRate);
    }
    public abstract double value(double t);

    @Override
    public List<Data> generateDiscreteRepresentation() {
        return new DiscreteSignal(getRangeStart(), getRangeLength(),
                getSampleRate() / getRangeLength(), this) {
            @Override
            public String getName() {
                return null;
            }
        }
                .generateDiscreteRepresentation();
    }

    public abstract String getName();
}
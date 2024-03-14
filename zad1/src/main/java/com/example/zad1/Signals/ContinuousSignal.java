package com.example.zad1.Signals;



public abstract class ContinuousSignal extends DiscreteSignal {

    public ContinuousSignal(double rangeStart, double rangeLength,double sampleRate){
        super(rangeStart, rangeLength, sampleRate);
    }
}

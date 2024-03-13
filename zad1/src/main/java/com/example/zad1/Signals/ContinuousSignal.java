package com.example.zad1.Signals;



public abstract class ContinuousSignal extends DiscreteSignal {

    private static final int NUMBER_OF_SAMPLES = 1000000;

    public ContinuousSignal(double rangeStart, double rangeLength) {
        super(rangeStart, rangeLength, NUMBER_OF_SAMPLES / rangeLength);
    }
}

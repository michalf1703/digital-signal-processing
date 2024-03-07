package com.example.zad1;

public class SinusoidalSignal extends ContinuousSignal{
    private final double period;

    public SinusoidalSignal(double amplitude, double startTime, double duration, double period) {
        super(amplitude, startTime, duration);
        this.period = period;
    }
}

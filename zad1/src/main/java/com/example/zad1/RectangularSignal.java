package com.example.zad1;

public class RectangularSignal extends ContinuousSignal{
    private final double period;
    private final double dutyCycle;

    public RectangularSignal(double amplitude, double startTime, double duration, double period, double dutyCycle) {
        super(amplitude, startTime, duration);
        this.period = period;
        this.dutyCycle = dutyCycle;
    }
}

package com.example.zad1;

public class SinusoidalSignal extends ContinuousSignal{
    protected double period;

    public SinusoidalSignal(double amplitude, double startTime, double duration, double period) {
        super(amplitude, startTime, duration);
        this.period = period;
    }
    @Override
    public double[] generateData(double time) {
    return new double[]{amplitude * Math.sin(2 * Math.PI * (time - startTime) / period)};
    }
}

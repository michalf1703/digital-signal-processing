package com.example.zad1;



public class ContinuousSignal extends Signal {
    protected double startTime;
    protected double duration;

    public ContinuousSignal(double amplitude, double startTime, double duration) {
        super(amplitude);
        this.startTime = startTime;
        this.duration = duration;
    }

    @Override
    public double[] generateData() {
        return new double[0];
    }
}
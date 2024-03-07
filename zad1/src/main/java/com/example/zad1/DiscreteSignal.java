
package com.example.zad1;

public class DiscreteSignal extends Signal {
    protected int sampleNumber;
    protected double startTime;
    protected double duration;

    public DiscreteSignal(double amplitude, double startTime, double duration, int sampleNumber) {
        super(amplitude);
        this.startTime = startTime;
        this.duration = duration;
        this.sampleNumber = sampleNumber;
    }

    @Override
    public double[] generateData() {
        return new double[0];
    }
}
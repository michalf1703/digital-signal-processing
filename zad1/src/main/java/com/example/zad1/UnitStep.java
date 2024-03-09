package com.example.zad1;

public class UnitStep extends ContinuousSignal{
    private final double stepTime;

    public UnitStep(double amplitude, double startTime, double duration, double stepTime) {
        super(amplitude, startTime, duration);
        this.stepTime = stepTime;
    }

    @Override
    public double[] generateData(double time) {
        return new double[0];
    }
}

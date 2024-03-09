package com.example.zad1;

public class ImpulseNoise extends DiscreteSignal {
    public ImpulseNoise(double amplitude, double startTime, double duration, double samplingFrequency, double probability) {
        super(amplitude);
    }

    @Override
    public double[] generateData(double time) {
        return super.generateData(time);
    }
}

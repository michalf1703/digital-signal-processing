package com.example.zad1;

public class UnitImpulse extends DiscreteSignal {
    private final double sampleNumberStep;
    private final double firstSampleNumber;
    private final double samplingFrequency;
    private final double l;

    public UnitImpulse(double amplitude, double sampleNumberStep, double firstSampleNumber, double samplingFrequency, double l) {
        super(amplitude);
        this.sampleNumberStep = sampleNumberStep;
        this.firstSampleNumber = firstSampleNumber;
        this.samplingFrequency = samplingFrequency;
        this.l = l;
    }
}

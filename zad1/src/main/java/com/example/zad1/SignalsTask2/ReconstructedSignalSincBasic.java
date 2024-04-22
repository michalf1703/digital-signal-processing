package com.example.zad1.SignalsTask2;


import com.example.zad1.Signals.ContinuousSignal;
import com.example.zad1.Signals.DiscreteSignal;

public class ReconstructedSignalSincBasic extends ContinuousSignal {

    private final DiscreteSignal sourceSignal;
    private final int N;

    public ReconstructedSignalSincBasic(DiscreteSignal sourceSignal, int N) {
        super(sourceSignal.getRangeStart(), sourceSignal.getRangeLength(), sourceSignal.getSampleRate());
        this.sourceSignal = sourceSignal;
        this.N = N;
    }

    @Override
    public double value(double t) {

        int index = (int) Math.floor((t - getRangeStart()) / getRangeLength() * sourceSignal.getNumberOfSamples());
        int firstSample = index - N / 2;
        int lastSample = firstSample + N;
        if (firstSample < 0) {
            lastSample = lastSample - firstSample;
            firstSample = 0;
            if (lastSample > sourceSignal.getNumberOfSamples()) {
                lastSample = sourceSignal.getNumberOfSamples();
            }
        } else if (lastSample > sourceSignal.getNumberOfSamples()) {
            firstSample = firstSample - (lastSample - sourceSignal.getNumberOfSamples());
            lastSample = sourceSignal.getNumberOfSamples();
            if (firstSample < 0) {
                firstSample = 0;
            }
        }

        final double step = getRangeLength() / sourceSignal.getNumberOfSamples();
        double sum = 0.0;
        for (int i = firstSample; i < lastSample; i++) {
            sum += sourceSignal.value(i) * sinc(t / step - i);
        }

        return sum;
    }

    @Override
    public String getName() {
        return "sygnał odtworzony metodą sinc";
    }

    private double sinc(double t) {
        if (t == 0.0) {
            return 1.0;
        } else {
            return Math.sin(Math.PI * t) / (Math.PI * t);
        }
    }
}
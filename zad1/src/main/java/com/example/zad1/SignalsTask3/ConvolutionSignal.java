package com.example.zad1.SignalsTask3;

import com.example.zad1.Signals.DiscreteSignal;

public class ConvolutionSignal extends DiscreteSignal {

    private final DiscreteSignal h;
    private final DiscreteSignal x;

    public ConvolutionSignal(DiscreteSignal h, DiscreteSignal x) {
        super(h.getRangeStart(),
                h.getNumberOfSamples() + x.getNumberOfSamples() - 1,
                h.getSampleRate());
        this.h = h;
        this.x = x;
    }



    @Override
    public double value(int i) {
        double sum = 0.0;
        int startK = Math.max(0, i - x.getNumberOfSamples() + 1);
        int endK = Math.min(h.getNumberOfSamples(), i + 1);
        for (int k = startK; k < endK; k++) {
            sum += h.value(k) * x.value(i - k);
        }
        System.out.println("Liczba probek1: " + h.getNumberOfSamples());
        System.out.println("Liczba probek2: " + x.getNumberOfSamples());
        return sum;
    }

    @Override
    public String getName() {
        return "szum impulsowy";
    }

    public double[] getImpulseResponse() {
        int M = h.getNumberOfSamples() + x.getNumberOfSamples() - 1;
        double[] response = new double[M];
        for (int n = 0; n < M; n++) {
            response[n] = value(n);
        }
        System.out.println("Liczba probek: " + M);
        return response;
    }
}

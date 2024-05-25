package com.example.zad1.SignalsTask3;

import com.example.zad1.Signals.DiscreteSignal;

public class CorrelationSignal extends DiscreteSignal{

    private final DiscreteSignal h;
    private final DiscreteSignal x;

    public CorrelationSignal(DiscreteSignal h, DiscreteSignal x) {
        super(h.getRangeStart(),
                h.getNumberOfSamples() + x.getNumberOfSamples() - 1,
                h.getSampleRate());
        this.h = h;
        this.x = x;
    }



    @Override
    public double value(int i) {
        i = i - (x.getNumberOfSamples() - 1);
        double sum = 0.0;
        int startK = Math.max(0, i);
        int endK = Math.min(h.getNumberOfSamples(), x.getNumberOfSamples() + i);
        for (int k = startK; k < endK; k++) {
            sum += h.value(k) * x.value(k - i);
        }
        return sum;
    }

    @Override
    public String getName() {
        return "CorrelationSignal";
    }
}

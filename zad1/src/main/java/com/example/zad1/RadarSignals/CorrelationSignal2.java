package com.example.zad1.RadarSignals;


public class CorrelationSignal2 extends DiscreteSignal2 {

    private final DiscreteSignal2 h;
    private final DiscreteSignal2 x;

    public CorrelationSignal2(DiscreteSignal2 h, DiscreteSignal2 x) {
        super(h.getRangeStart(),
                h.getNumberOfSamples() + x.getNumberOfSamples() - 1,
                h.getSampleRate());
        this.h = h;
        this.x = x;
    }

    @Override
    public double value(int i) {
        /* translate to real indexes */
        i = i - (x.getNumberOfSamples() - 1);

        /* compute */
        double sum = 0.0;
        int startK = Math.max(0, i);
        int endK = Math.min(h.getNumberOfSamples(), x.getNumberOfSamples() + i);
        for (int k = startK; k < endK; k++) {
            sum += h.value(k) * x.value(k - i);
        }
        return sum;
    }

}

package com.example.zad1.SignalsTask2;

import com.example.zad1.Signals.DiscreteSignal;

public class QuantizedSignalTruncated extends QuantizedSignal {

    public QuantizedSignalTruncated(final DiscreteSignal sourceSignal, final int numberOfLevels) {
        super(sourceSignal, numberOfLevels);
    }

    @Override
    public double rounding(final double x) {
        return Math.floor(x);
    }

    @Override
    public String getName() {
        return "sygnał kwantowany ucięty";
    }
}

package com.example.zad1.SignalsTask2;


import com.example.zad1.Signals.DiscreteSignal;

public class QuantizedSignalRounded extends QuantizedSignal {

    public QuantizedSignalRounded(final DiscreteSignal sourceSignal, final int numberOfLevels) {
        super(sourceSignal, numberOfLevels);
    }

    @Override
    public double rounding(final double x) {
        return Math.round(x);
    }

    @Override
    public String getName() {
        return "sygnał kwantowany zaokrąglony";
    }
}

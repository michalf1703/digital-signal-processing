package com.example.zad1.SignalsTask2;

import com.example.zad1.Signals.ContinuousSignal;
import com.example.zad1.Signals.DiscreteSignal;

public class ReconstructedSignalFirstOrderHold extends ContinuousSignal {

    private final DiscreteSignal sourceSignal;

    public ReconstructedSignalFirstOrderHold(DiscreteSignal sourceSignal) {
        super(sourceSignal.getRangeStart(), sourceSignal.getRangeLength(), sourceSignal.getSampleRate());
        this.sourceSignal = sourceSignal;
    }

    @Override
    public double value(double t) {
        int index = (int) Math.floor((t - getRangeStart()) / getRangeLength() * sourceSignal.getNumberOfSamples());
        if (index < sourceSignal.getNumberOfSamples() - 1) {
            return (t - sourceSignal.argument(index)) /
                    (sourceSignal.argument(index + 1) - sourceSignal.argument(index)) *
                    (sourceSignal.value(index + 1) - sourceSignal.value(index)) + sourceSignal.value(index);
        } else {
            return sourceSignal.value(index);
        }
    }

    @Override
    public String getName() {
        return "sygnał odtworzony metodą pierwszego rzędu";
    }
}
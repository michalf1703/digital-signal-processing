package com.example.zad1.SignalsTask2;

import com.example.zad1.Signals.ContinuousSignal;
import com.example.zad1.Signals.DiscreteSignal;

public class ReconstructedSignalZeroOrderHold extends ContinuousSignal {

    private final DiscreteSignal sourceSignal;

    public ReconstructedSignalZeroOrderHold(DiscreteSignal sourceSignal) {
        super(sourceSignal.getRangeStart(), sourceSignal.getRangeLength(), sourceSignal.getSampleRate());
        this.sourceSignal = sourceSignal;
    }

    public double value(double t) {
        int index = (int) Math.floor((t - getRangeStart()) / getRangeLength() * sourceSignal.getNumberOfSamples());
        return sourceSignal.value(index);
    }

    @Override
    public String getName() {
        return "sygnał odtworzony metodą zero order hold";
    }
}

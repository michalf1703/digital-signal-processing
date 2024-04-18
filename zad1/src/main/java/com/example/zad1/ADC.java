package com.example.zad1;

import com.example.zad1.Signals.ContinuousSignal;
import com.example.zad1.Signals.DiscreteSignal;
import com.example.zad1.SignalsTask2.QuantizedSignalRounded;
import com.example.zad1.SignalsTask2.QuantizedSignalTruncated;

import java.io.Serializable;

public class ADC implements Serializable {

    public DiscreteSignal sampling(ContinuousSignal signal, double sampleRate) {
        return new DiscreteSignal(signal.getRangeStart(), signal.getRangeLength(), sampleRate, signal) {
            @Override
            public String getName() {
                return "szum impulsowy";
            }
        };
    }

    public DiscreteSignal roundingQuantization(DiscreteSignal signal, int numberOfLevels) {
        return new QuantizedSignalRounded(signal, numberOfLevels);
    }

    public DiscreteSignal truncatingQuantization(DiscreteSignal signal, int numberOfLevels) {
        return new QuantizedSignalTruncated(signal, numberOfLevels);
    }
}
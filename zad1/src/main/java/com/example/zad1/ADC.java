package com.example.zad1;

import com.example.zad1.Signals.ContinuousSignal;
import com.example.zad1.Signals.DiscreteSignal;
import com.example.zad1.SignalsTask2.QuantizedSignalRounded;
import com.example.zad1.SignalsTask2.QuantizedSignalTruncated;

public class ADC {

    public DiscreteSignal sampling(ContinuousSignal signal, double sampleRate) {
        return new DiscreteSignal(signal.getRangeStart(), signal.getRangeLength(), sampleRate, signal) {
            @Override
            public String getName() {
                return null;
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
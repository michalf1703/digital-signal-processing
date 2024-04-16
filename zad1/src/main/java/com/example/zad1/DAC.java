package com.example.zad1;

import com.example.zad1.Signals.ContinuousSignal;
import com.example.zad1.Signals.DiscreteSignal;
import com.example.zad1.SignalsTask2.ReconstructedSignalFirstOrderHold;
import com.example.zad1.SignalsTask2.ReconstructedSignalSincBasic;
import com.example.zad1.SignalsTask2.ReconstructedSignalZeroOrderHold;

public class DAC {

    public ContinuousSignal zeroOrderHold(DiscreteSignal signal) {
        return new ReconstructedSignalZeroOrderHold(signal);
    }

    public ContinuousSignal firstOrderHold(DiscreteSignal signal) {
        return new ReconstructedSignalFirstOrderHold(signal);
    }

    public ContinuousSignal sincBasic(DiscreteSignal signal, int N) {
        return new ReconstructedSignalSincBasic(signal, N);
    }
}
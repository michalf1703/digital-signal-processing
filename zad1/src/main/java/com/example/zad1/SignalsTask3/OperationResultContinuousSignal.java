package com.example.zad1.SignalsTask3;

import com.example.zad1.Signals.ContinuousSignal;
import com.example.zad1.Signals.Operation;
import com.example.zad1.Signals.OperationSignal;

import java.io.Serializable;

public class OperationResultContinuousSignal extends ContinuousSignal implements Serializable {

    private final ContinuousSignal s1;
    private final ContinuousSignal s2;
    private final Operation operation;

    public OperationResultContinuousSignal(ContinuousSignal s1, ContinuousSignal s2,
                                           Operation operation) {
        super(s1.getRangeStart(), s1.getRangeLength(), s1.getSampleRate());
        this.s1 = s1;
        this.s2 = s2;
        this.operation = operation;

        if (s1.getRangeLength() != s2.getRangeLength() ||
                s1.getRangeStart() != s2.getRangeStart()) {
            throw new OperationSignal.NotSameLengthException();
        }
    }

    @Override
    public void setRangeStart(double rangeStart) {
        super.setRangeStart(rangeStart);
        this.s1.setRangeStart(rangeStart);
        this.s2.setRangeStart(rangeStart);
    }

    @Override
    public double value(double t) {
        return operation.operation(s1.value(t), s2.value(t));
    }

    @Override
    public String getName() {
        return null;
    }
}
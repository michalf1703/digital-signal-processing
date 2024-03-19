package com.example.zad1.Signals;

import com.example.zad1.Base.Data;

public class OperationSignal extends Signal {

    private Signal s1;
    private Signal s2;
    private Operation operation;
    private double sampleRate = 0;

    public OperationSignal(Signal s1, Signal s2, Operation operation) {
        super(s1.getData().size());

        if (s1.getData().size() != s2.getData().size()) {
            throw new NotSameLengthException();
        }

        this.s1 = s1;
        this.s2 = s2;
        this.operation = operation;
    }
    @Override
    public double getSampleRate() {
        return this.sampleRate;
    }

    @Override
    public void generate() {
        s1.generate();
        s2.generate();

        for (int i = 0; i < data.length; i++) {
            data[i] = new Data(s1.getData().get(i).getX(),
                    operation.operation(s1.getData().get(i).getY(),
                            s2.getData().get(i).getY()));
        }
    }

    @Override
    public String getName() {
        return null;
    }

    public class NotSameLengthException extends RuntimeException {
    }
}
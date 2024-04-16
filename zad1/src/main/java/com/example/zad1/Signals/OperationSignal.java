package com.example.zad1.Signals;

import com.example.zad1.Base.Data;

import java.util.ArrayList;
import java.util.List;

public class OperationSignal extends Signal {

    private Signal s1;
    private Signal s2;
    private Operation operation;
    private double sampleRate = 0.0;

    public OperationSignal(Signal s1, Signal s2, Operation operation) {
        super(s1.getRangeStart(), s1.getRangeLength(), s1.getSampleRate());
        this.s1 = s1;
        this.s2 = s2;
        this.operation = operation;
    }
    @Override
    public double getSampleRate() {
        return this.sampleRate;
    }

    @Override
    public List<Data> generateDiscreteRepresentation() {
        List<Data> data1 = s1.generateDiscreteRepresentation();
        List<Data> data2 = s2.generateDiscreteRepresentation();
        List<Data> resultData = new ArrayList<>();

        for (int i = 0; i < data1.size(); i++) {
            resultData
                    .add(new Data(data1.get(i).getX(), operation.operation(data1.get(i).getY(), data2.get(i).getY())));
        }
        return resultData;
    }

    @Override
    public String getName() {
        return null;
    }

    public static class NotSameLengthException extends RuntimeException {
    }
}
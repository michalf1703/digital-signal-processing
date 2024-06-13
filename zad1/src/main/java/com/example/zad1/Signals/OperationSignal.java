package com.example.zad1.Signals;

import com.example.zad1.Base.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OperationSignal extends Signal implements Serializable {

        private Signal s1;
        private Signal s2;
        private String operationType;
        private double sampleRate = 0.0;

        public OperationSignal(Signal s1, Signal s2, String operationType) {
            super(s1.getRangeStart(), s1.getRangeLength(), s1.getSampleRate());
            this.s1 = s1;
            this.s2 = s2;
            this.operationType = operationType;
        }

    private double performOperation(double a, double b) {
        switch (operationType) {
            case "dodawanie":
                return a + b;
            case "odejmowanie":
                return a - b;
            case "mnozenie":
                return a * b;
            case "dzielenie":
                return a / b;
            default:
                throw new IllegalArgumentException("Nieznany typ operacji: " + operationType);
        }
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
                    .add(new Data(data1.get(i).getX(), performOperation(data1.get(i).getY(), data2.get(i).getY())));
        }
        return resultData;
    }

    public ContinuousSignal toContinuousSignal() {
        return new ContinuousSignal(getRangeStart(), getRangeLength(), getSampleRate()) {
            @Override
            public double value(double t) {
                return performOperation(((ContinuousSignal)s1).value(t), ((ContinuousSignal)s2).value(t));
            }

            @Override
            public String getName() {
                return OperationSignal.this.getName();
            }
        };
    }

    @Override
    public String getName() {
        return "Wynik operacji " + operationType + " sygnałów " + s1.getName() + " i " + s2.getName();
    }

    public static class NotSameLengthException extends RuntimeException {
    }
}
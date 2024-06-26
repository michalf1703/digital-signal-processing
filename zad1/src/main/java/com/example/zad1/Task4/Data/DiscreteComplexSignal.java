package com.example.zad1.Task4.Data;


import com.example.zad1.Base.Data;
import org.apache.commons.math3.complex.Complex;

import java.util.ArrayList;
import java.util.List;

public abstract class DiscreteComplexSignal extends ComplexSignal {

    private final double sampleRate;
    private final int numberOfSamples;

    public DiscreteComplexSignal(double rangeStart, int numberOfSamples, double sampleRate) {
        super(rangeStart, numberOfSamples * (1.0 / sampleRate), sampleRate);
        this.sampleRate = sampleRate;
        this.numberOfSamples = numberOfSamples;
    }

    public double getSampleRate() {
        return sampleRate;
    }

    public int getNumberOfSamples() {
        return numberOfSamples;
    }

    public abstract Complex value(int n);

    public double argument(int n) {
        return n * sampleRate / numberOfSamples;
    }

    @Override
    public List<Data> generateDiscreteRepresentation() {
        List<Data> data = new ArrayList<>();
        for (int i = 0; i < numberOfSamples; i++) {
            double value;
            switch (getDiscreteRepresentationType()) {
                case ABS:
                    value = value(i).abs();
                    break;
                case ARG:
                    value = value(i).getArgument();
                    break;
                case REAL:
                    value = value(i).getReal();
                    break;
                default:
                    value = value(i).getImaginary();
            }
            data.add(new Data(argument(i), value));
        }
        return data;
    }
}
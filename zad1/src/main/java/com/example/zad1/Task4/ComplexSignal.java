package com.example.zad1.Task4;

import com.example.zad1.Signals.Signal;

public abstract class ComplexSignal extends Signal {

    public enum DiscreteRepresentationType {
        ABS,
        REAL,
        IMAGINARY,
        ARG
    }

    private DiscreteRepresentationType discreteRepresentationType;

    public ComplexSignal(final double rangeStart, final double rangeLength, final double sampleRate) {
        super(rangeStart, rangeLength, sampleRate);
    }

    public DiscreteRepresentationType getDiscreteRepresentationType() {
        return discreteRepresentationType;
    }

    public void setDiscreteRepresentationType(DiscreteRepresentationType discreteRepresentationType) {
        this.discreteRepresentationType = discreteRepresentationType;
    }
}

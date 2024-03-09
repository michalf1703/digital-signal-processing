
package com.example.zad1;

public abstract class DiscreteSignal extends Signal {
    public DiscreteSignal(double amplitude) {
        super(amplitude);
    }

    @Override
    public double[] generateData(double time) {
        return new double[0];
    }
}
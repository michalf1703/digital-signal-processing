package com.example.zad1;

import com.example.zad1.Signals.ContinuousSignal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CombinedSinusoidalSignal extends ContinuousSignal implements Serializable {

    private final List<SinusoidalComponent> components;
    private final double sampleRate;

    public CombinedSinusoidalSignal(double rangeStart, double rangeLength, double sampleRate) {
        super(rangeStart, rangeLength, sampleRate);
        this.sampleRate = sampleRate;
        this.components = new ArrayList<>();
    }

    public void addComponent(double amplitude, double term, double phase) {
        components.add(new SinusoidalComponent(amplitude, term, phase));
    }

    @Override
    public double value(double t) {
        double result = 0;
        for (SinusoidalComponent component : components) {
            result += component.amplitude * Math.sin((2.0 * Math.PI / component.term) * (t - rangeStart) + component.phase);
        }
        return result;
    }

    @Override
    public double getSampleRate() {
        return this.sampleRate;
    }

    @Override
    public String getName() {
        return "Combined sinusoidal signal";
    }

    @Override
    public int getNumberOfSamples() {
        return (int) (getRangeLength() * getSampleRate());
    }

    private static class SinusoidalComponent {
        final double amplitude;
        final double term;
        final double phase;

        SinusoidalComponent(double amplitude, double term, double phase) {
            this.amplitude = amplitude;
            this.term = term;
            this.phase = phase;
        }
    }
}

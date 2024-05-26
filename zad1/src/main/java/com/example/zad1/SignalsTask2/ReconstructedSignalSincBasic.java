package com.example.zad1.SignalsTask2;

import com.example.zad1.Signals.ContinuousSignal;
import com.example.zad1.Signals.DiscreteSignal;

public class ReconstructedSignalSincBasic extends ContinuousSignal {

    private final DiscreteSignal sourceSignal;
    private final int neighbourSamples;

    public ReconstructedSignalSincBasic(DiscreteSignal sourceSignal, int neighbourSamples) {
        super(sourceSignal.getRangeStart(), sourceSignal.getRangeLength(), sourceSignal.getSampleRate());
        this.sourceSignal = sourceSignal;
        this.neighbourSamples = neighbourSamples;
    }

    @Override
    public double value(double t) {
        double sum = 0.0;
        int sampleRate = (int) sourceSignal.getSampleRate();
        double startTime = sourceSignal.getRangeStart();

        // Calculate the index of the sample that corresponds to time t
        int centerIndex = (int) Math.round((t - startTime) * sampleRate);

        // Calculate the number of samples to consider on each side
        int halfNeighbourSamples = neighbourSamples / 2;

        // Ensure the start index is not negative
        int startIndex = Math.max(0, centerIndex - halfNeighbourSamples);
        // Ensure the end index is within bounds
        int endIndex = Math.min(sourceSignal.getNumberOfSamples() - 1, centerIndex + halfNeighbourSamples);

        // Logging information
        System.out.println("Time t: " + t);
        System.out.println("Center Index: " + centerIndex);
        System.out.println("Start Index: " + startIndex);
        System.out.println("End Index: " + endIndex);

        // Sum contributions from the calculated range
        for (int i = startIndex; i <= endIndex; i++) {
            // Ensure the sample index is within bounds
            if (i >= 0 && i < sourceSignal.getNumberOfSamples()) {
                double sampleTime = sourceSignal.argument(i);
                double sampleValue = sourceSignal.value(i);
                sum += sampleValue * sinc((t - sampleTime) * sampleRate);
                System.out.println("Sample Index: " + i + ", Sample Time: " + sampleTime + ", Sample Value: " + sampleValue);
            }
        }

        System.out.println("Sum of contributions: " + sum);
        return sum;
    }

    @Override
    public String getName() {
        return "sygnał odtworzony metodą sinc";
    }

    private double sinc(double t) {
        if (t == 0.0) {
            return 1.0;
        } else {
            return Math.sin(Math.PI * t) / (Math.PI * t);
        }
    }
}
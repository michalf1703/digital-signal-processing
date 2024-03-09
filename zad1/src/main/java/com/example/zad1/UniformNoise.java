package com.example.zad1;

import java.util.Random;

public class UniformNoise extends ContinuousSignal{


    public UniformNoise(double amplitude, double startTime, double duration) {
        super(amplitude, startTime, duration);
    }

    @Override
    public double[] generateData(double time) {
        int numberOfSamples = (int) Math.round(duration);
        double[] data = new double[numberOfSamples];
        Random random = new Random();
        double range = 2 * amplitude; // Zakres losowych wartości
        double minValue = -amplitude; // Minimalna wartość sygnału
        for (int i = 0; i < numberOfSamples; i++) {
            data[i] = random.nextDouble() * range + minValue; // Generowanie losowych wartości w zakresie [-Amax, Amax]
        }
        return data;
    }
}

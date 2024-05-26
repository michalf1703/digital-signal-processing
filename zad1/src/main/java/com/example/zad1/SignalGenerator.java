package com.example.zad1;

public class SignalGenerator {
    public static double[] generateSignal(int length, double frequency, double samplingRate) {
        double[] signal = new double[length];
        for (int i = 0; i < length; i++) {
            signal[i] = Math.sin(2 * Math.PI * frequency * i / samplingRate);
        }
        return signal;
    }
}

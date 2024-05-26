package com.example.zad1;

public class SignalSampler {
    public static double[] sampleSignal(double[] signal, double samplingRate, int bufferLength) {
        double[] buffer = new double[bufferLength];
        for (int i = 0; i < bufferLength; i++) {
            buffer[i] = signal[i];
        }
        return buffer;
    }
}

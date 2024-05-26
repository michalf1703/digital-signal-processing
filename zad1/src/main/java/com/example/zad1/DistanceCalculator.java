package com.example.zad1;

public class DistanceCalculator {
    public static double calculateDistance(int maxIndex, double samplingRate, double waveSpeed) {
        double timeDelay = maxIndex / samplingRate;
        return (waveSpeed * timeDelay) / 2;
    }
}

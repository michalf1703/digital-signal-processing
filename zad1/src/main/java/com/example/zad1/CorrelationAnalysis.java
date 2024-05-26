package com.example.zad1;

public class CorrelationAnalysis {
    public static double[] crossCorrelate(double[] signal1, double[] signal2) {
        int length = signal1.length;
        double[] result = new double[length];
        for (int lag = 0; lag < length; lag++) {
            double sum = 0;
            for (int i = 0; i < length - lag; i++) {
                sum += signal1[i] * signal2[i + lag];
            }
            result[lag] = sum;
        }
        return result;
    }

    public static int findMaxIndex(double[] array) {
        int maxIndex = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > array[maxIndex]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }
}



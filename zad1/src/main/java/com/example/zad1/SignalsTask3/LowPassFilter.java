package com.example.zad1.SignalsTask3;

import com.example.zad1.window.Hamming;
import com.example.zad1.window.Rectangular;
import com.example.zad1.window.Window;

public class LowPassFilter {
    final int M;
    final double fo;
    final double K;
    final Window window;

    public LowPassFilter(int M, double fo, double sampleRate, Window window) {
        if ((M & 0x01) == 0) {
            throw new IllegalArgumentException("M must be an odd value!");
        }
        this.M = M;
        this.fo = fo;
        this.K = sampleRate / fo;
        this.window = window;
    }

    public double value(int n) {
        int c = (M - 1) / 2;
        double result;
        if (n == c) {
            result = 2.0 / K;
        } else {
            result = Math.sin(2.0 * Math.PI * (n - c) / K) / (Math.PI * (n - c));
        }
        return result * window.w(n);
    }

    public String getName() {
        return "filtr dolnoprzepustowy";
    }

    public double[] getImpulseResponse() {
        double[] response = new double[M];
        for (int n = 0; n < M; n++) {
            response[n] = value(n);
        }
        return response;
    }

}

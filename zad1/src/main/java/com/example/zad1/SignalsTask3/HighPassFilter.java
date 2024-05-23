package com.example.zad1.SignalsTask3;

import com.example.zad1.window.Window;

public class HighPassFilter extends LowPassFilter{

    public HighPassFilter(double sampleRate, int M, double fo, Window window) {
        super(sampleRate, M, fo, window);
    }

    @Override
    public double value(int n) {
        return super.value(n) * ((n & 0x01) == 1 ? -1.0 : 1.0);
    }
}

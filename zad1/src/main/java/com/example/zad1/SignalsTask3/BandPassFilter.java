package com.example.zad1.SignalsTask3;

import com.example.zad1.window.Window;

public class BandPassFilter extends LowPassFilter{

    public BandPassFilter(double sampleRate, int M, double fo, Window window) {
        super(sampleRate, M, fo, window);
    }

    @Override
    public double value(int n) {
        return super.value(n) * 2.0 * Math.sin(Math.PI * n / 2.0);
    }
}

package com.example.zad1.SignalsTask3;

import com.example.zad1.window.Window;

public class BandPassFilter extends LowPassFilter{

    public BandPassFilter( int M, double fo, double sampleRate, Window window) {
        super( M, fo, sampleRate, window);
    }

    @Override
    public double value(int n) {
        return super.value(n) * 2.0 * Math.sin(Math.PI * n / 2.0);
    }
}

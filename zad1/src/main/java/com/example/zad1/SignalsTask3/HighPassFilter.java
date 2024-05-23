package com.example.zad1.SignalsTask3;

import com.example.zad1.window.Window;

public class HighPassFilter extends LowPassFilter{

    public HighPassFilter(int M, double fo, double sampleRate,Window window) {
        super(M, fo,sampleRate, window);
    }

    @Override
    public double value(int n) {
        return super.value(n) * ((n & 0x01) == 1 ? -1.0 : 1.0);
    }

    public String getName() {
        return "filtr górnoprzepustowy";
    }
}

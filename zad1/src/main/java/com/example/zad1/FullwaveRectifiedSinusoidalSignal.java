package com.example.zad1;

public class FullwaveRectifiedSinusoidalSignal extends SinusoidalSignal {
    public FullwaveRectifiedSinusoidalSignal(double amplitude, double startTime, double duration, double period) {
        super(amplitude, startTime, duration, period);
    }
    @Override
    public double[] generateData(double time) {
        return new double[]{amplitude * Math.sin(2 * Math.PI * (time - startTime) / period)};
    }
}

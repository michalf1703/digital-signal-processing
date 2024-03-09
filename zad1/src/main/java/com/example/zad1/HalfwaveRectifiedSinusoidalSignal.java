package com.example.zad1;

public class HalfwaveRectifiedSinusoidalSignal extends SinusoidalSignal {
    public HalfwaveRectifiedSinusoidalSignal(double amplitude, double startTime, double duration, double period) {
        super(amplitude, startTime, duration, period);
    }

    @Override
    public double[] generateData(double time) {
        return new double[]{(0.5 * amplitude
                * (Math.sin((2.0 * Math.PI / period) * (time - startTime))
                + Math.abs(Math.sin((2.0 * Math.PI / period) * (time - startTime)))))};
    }
}

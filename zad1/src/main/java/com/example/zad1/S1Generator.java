package com.example.zad1;


import com.example.zad1.Signals.ContinuousSignal;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class S1Generator extends ContinuousSignal {

    private double rangeStart;
    private double rangeLength;
    private double sampleRate;

    public S1Generator(double rangeStart, double rangeLength, double sampleRate) {
        super(rangeStart, rangeLength, sampleRate);
        this.rangeStart = rangeStart;
        this.rangeLength = rangeLength;
        this.sampleRate = sampleRate;
    }

    @Override
    public double value(double t) {
        double signal = 2.0 * Math.sin((2.0 * Math.PI * t) / 2.0 + Math.PI / 2.0)
                + 5.0 * Math.sin((2.0 * Math.PI * t) / 0.5 + Math.PI / 2.0);
        return signal;
    }

    @Override
    public String getName() {
        return "Sygnał S1";
    }

    public void generateAndSaveSignal(String filename) {
        try {
            FileWriter fileWriter = new FileWriter(filename);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            for (double t = rangeStart; t <= rangeStart + rangeLength; t += 1.0 / sampleRate) {
                double signal = value(t); // Wywołujemy metodę value(double t) do obliczenia sygnału
                printWriter.printf("%.4f %.4f%n", t, signal);
            }

            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        double rangeStart = 0.0;
        double rangeLength = 10.0;
        double sampleRate = 100.0;

        S1Generator generator = new S1Generator(rangeStart, rangeLength, sampleRate);
        generator.generateAndSaveSignal("signal_s1.txt");
    }
}
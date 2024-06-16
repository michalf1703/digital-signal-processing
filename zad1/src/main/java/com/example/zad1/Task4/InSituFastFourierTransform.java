package com.example.zad1.Task4;

import org.apache.commons.math3.complex.Complex;

public class InSituFastFourierTransform extends ComplexTransform {

    @Override
    public Complex[] transform(Complex[] x) {
        mixSamples(x); // Mieszanie próbek, wspólne dla obu metod FFT

        int N = x.length;

        // Główna pętla FFT (DIF FFT)
        for (int s = 1; s <= Math.log(N) / Math.log(2); s++) {
            int m = 1 << s; // m = 2^s
            Complex wm = new Complex(Math.cos(2 * Math.PI / m), Math.sin(2 * Math.PI / m));
            for (int k = 0; k < N; k += m) {
                Complex w = Complex.ONE;
                for (int j = 0; j < m / 2; j++) {
                    if (k + j + m / 2 < N) { // Sprawdzenie warunku granicznego
                        Complex t = w.multiply(x[k + j + m / 2]);
                        Complex u = x[k + j];
                        x[k + j] = u.add(t);
                        x[k + j + m / 2] = u.subtract(t);
                    }
                    w = w.multiply(wm);
                }
            }
        }
        for (int i = 0; i < N; i++) {
            x[i] = x[i].divide(N);
        }
        return x;
    }


    protected void mixSamples(Complex[] samples) {
        int N = samples.length;
        // Obliczanie liczby bitów długości N
        int numberOfBits = Integer.numberOfTrailingZeros(N);

        // Mieszanie
        for (int i = 0; i < N; i++) {
            int j = Integer.reverse(i) >>> (32 - numberOfBits);
            if (j > i) {
                Complex tmp = samples[i];
                samples[i] = samples[j];
                samples[j] = tmp;
            }
        }
    }
}

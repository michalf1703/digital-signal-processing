package com.example.zad1.Task4;

import org.apache.commons.math3.complex.Complex;

public class RecursiveFastFourierTransform extends ComplexTransform{

    @Override
    public Complex[] transform(Complex[] x) {
        Complex[] X = new Complex[x.length];
        for (int m = 0; m < x.length; m++) {
            X[m] = recursiveFFT(x, m);
        }
        return X;
    }

    protected Complex recursiveFFT(Complex[] x, int m) {

        int N = x.length;

        if (N == 1) {
            return x[0];
        }

        Complex[] even = new Complex[N / 2];
        Complex[] odd = new Complex[N / 2];
        int evenIterator = 0, oddIterator = 0;
        for (int i = 0; i < N; i++) {
            if (i % 2 == 0) {
                even[evenIterator++] = x[i];
            } else {
                odd[oddIterator++] = x[i];
            }
        }

        Complex a = recursiveFFT(even, m);
        Complex b = recursiveFFT(odd, m);

        double Warg = 2.0 * Math.PI / N;
        Complex w = new Complex(Math.cos(Warg), Math.sin(Warg)).pow(-m);

        return a.add(w.multiply(b));
    }
}

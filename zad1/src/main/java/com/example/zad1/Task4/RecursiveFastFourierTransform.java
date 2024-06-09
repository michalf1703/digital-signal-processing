package com.example.zad1.Task4;

import org.apache.commons.math3.complex.Complex;

public class RecursiveFastFourierTransform extends ComplexTransform{

    @Override
    public Complex[] transform(Complex[] x) {
        return recursiveFFT(x, 0);
    }

    /**
     * This is recursive implementation of FFT, each call require new
     * memory allocation
     */
    protected Complex[] recursiveFFT(Complex[] x, int m) {

        int N = x.length;

        /* stop condition */
        if (N == 1) {
            return x;
        }

        /* split into odd indexed and even indexed samples */
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

        /* call recursively for each group of samples */
        Complex[] a = recursiveFFT(even, m);
        Complex[] b = recursiveFFT(odd, m);

        /* calculate value of W_{N}^{-m} */
        double Warg = 2.0 * Math.PI / N;
        Complex w = new Complex(Math.cos(Warg), Math.sin(Warg)).pow(-m);

        /* return result */
        for (int i = 0; i < N / 2; i++) {
            Complex tmp = a[i].add(b[i]);
            x[i + N / 2] = a[i].subtract(b[i]);
            x[i] = tmp;
            x[i + N / 2] = x[i + N / 2].multiply(w.pow(-i));
        }

        return x;
    }
}

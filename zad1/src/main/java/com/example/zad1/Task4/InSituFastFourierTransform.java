package com.example.zad1.Task4;

import com.example.zad1.Task4.Data.ComplexTransform;
import org.apache.commons.math3.complex.Complex;

public class InSituFastFourierTransform extends ComplexTransform {


    @Override
    public Complex[] transform(Complex[] x) {
        int N = x.length;
        int tree_levels = Integer.numberOfTrailingZeros(N);
        System.out.println("tree_levels: " + tree_levels);
        for (int s = tree_levels; s >= 1; s--) {
            int m = 1 << s;
            int halfM = m / 2;
            Complex wm = new Complex(Math.cos(2 * Math.PI / m), -Math.sin(2 * Math.PI / m));
            for (int k = 0; k < N; k += m) {
                Complex w = Complex.ONE;
                for (int j = 0; j < halfM; j++) {
                    Complex u = x[k + j];
                    Complex t = x[k + j + halfM];
                    x[k + j] = u.add(t);
                    x[k + j + halfM] = w.multiply(u.subtract(t));
                    w = w.multiply(wm);
                }
            }
        }
        for (int i = 0; i < N; i++) {
            int j = Integer.reverse(i) >>> (32 - tree_levels);
            if (j > i) {
                Complex tmp = x[i];
                x[i] = x[j];
                x[j] = tmp;
            }
        }
        for (int i = 0; i < N; i++) {
            x[i] = x[i].divide(N);
        }

        return x;
    }
}

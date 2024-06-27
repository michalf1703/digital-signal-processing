package com.example.zad1.Task4;

import com.example.zad1.Task4.Data.RealTransform;

public class WalshHadamardTransform extends RealTransform {

    @Override
    public double[] transform(final double[] x) {
        int N = x.length;
        int m = Integer.numberOfTrailingZeros(N);
        int size = 1 << m; // Rozmiar macierzy Hadamarda Hm

        double[] X = new double[size];
        double[][] Hm = generateHadamardMatrix(m);

        // Wykonanie transformacji WH-1: X = Hm * x
        for (int i = 0; i < size; i++) {
            double sum = 0.0;
            for (int j = 0; j < size; j++) {
                sum += Hm[i][j] * x[j];
            }
            X[i] = sum;
        }

        return X;
    }

    protected double[][] generateHadamardMatrix(int m) {
        int size = 1 << m; // Rozmiar macierzy Hadamarda Hm
        double[][] Hm = new double[size][size];

        // Bazowy przypadek dla m = 0
        Hm[0][0] = 1.0;

        // Generowanie macierzy Hadamarda Hm rekurencyjnie
        for (int s = 1; s <= m; s++) {
            int m_pow = 1 << (s - 1); // 2^(s-1)
            int m_pow2 = m_pow * 2; // 2^s

            // Kopiowanie istniejącej macierzy do odpowiednich części
            for (int i = 0; i < m_pow; i++) {
                for (int j = 0; j < m_pow; j++) {
                    Hm[i][j] = Hm[i][j];
                    Hm[i][j + m_pow] = Hm[i][j];
                    Hm[i + m_pow][j] = Hm[i][j];
                    Hm[i + m_pow][j + m_pow] = -Hm[i][j];
                }
            }
        }

        return Hm;
    }
}

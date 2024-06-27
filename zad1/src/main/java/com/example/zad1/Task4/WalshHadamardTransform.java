package com.example.zad1.Task4;

import com.example.zad1.Task4.Data.RealTransform;

public class WalshHadamardTransform extends RealTransform {

    @Override
    public double[] transform(final double[] x) {
        int N = x.length;
        int m = Integer.numberOfTrailingZeros(N);
        double[] X = new double[N];
        double[][] Hm = new double[N][N];
        Hm[0][0] = 1.0;

        for (int s = 1; s <= m; s++) {
            int m_pow = 1 << (s - 1);
            for (int i = 0; i < m_pow; i++) {
                for (int j = 0; j < m_pow; j++) {
                    Hm[i][j] = Hm[i][j];
                    Hm[i][j + m_pow] = Hm[i][j];
                    Hm[i + m_pow][j] = Hm[i][j];
                    Hm[i + m_pow][j + m_pow] = -Hm[i][j];
                }
            }
        }

        for (int i = 0; i < N; i++) {
            double sum = 0.0;
            for (int j = 0; j < N; j++) {
                sum += Hm[i][j] * x[j];
            }
            X[i] = sum;
        }

        return X;
    }

}

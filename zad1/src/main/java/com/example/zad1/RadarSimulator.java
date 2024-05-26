package com.example.zad1;

public class RadarSimulator {
    public static void main(String[] args) {
        // Parametry symulatora
        double frequency = 10; // Hz
        double samplingRate = 20; // Hz
        int bufferLength = 500;
        double waveSpeed = 3000; // m/s, prędkość sygnału

        // Parametry eksperymentu
        double timeUnit = 1.0; // sekundy
        int repetitions = 10;
        double objectSpeed = 20.0; // m/s, prędkość śledzonego obiektu
        double initialDistance = 20.0; // m, początkowa odległość obiektu

        // Generowanie sygnału sondującego
        double[] transmittedSignal = SignalGenerator.generateSignal(bufferLength, frequency, samplingRate);

        for (int i = 0; i < repetitions; i++) {
            // Obliczenie rzeczywistej odległości
            double actualDistance = initialDistance + objectSpeed * (i * timeUnit);

            // Symulacja odbicia sygnału
            double travelTime = 2 * actualDistance / waveSpeed;
            int delaySamples = (int) (travelTime * samplingRate);

            // Sprawdzenie, czy delaySamples nie przekracza długości bufora
            if (delaySamples >= bufferLength) {
                System.out.println("Powtórzenie " + (i + 1));
                System.out.println("Rzeczywista odległość: " + actualDistance + " metrów");
                System.out.println("Obliczona odległość: przekroczenie bufora");
                System.out.println("Czas: " + (i * timeUnit) + " sekund\n");
                continue;
            }

            double[] receivedSignal = new double[bufferLength];
            System.arraycopy(transmittedSignal, 0, receivedSignal, delaySamples, bufferLength - delaySamples);

            // Analiza korelacyjna
            double[] correlation = CorrelationAnalysis.crossCorrelate(transmittedSignal, receivedSignal);
            int maxIndex = CorrelationAnalysis.findMaxIndex(correlation);

            // Obliczenie odległości
            double calculatedDistance = DistanceCalculator.calculateDistance(maxIndex, samplingRate, waveSpeed);

            // Zapis wyników
            System.out.println("Powtórzenie " + (i + 1));
            System.out.println("Rzeczywista odległość: " + actualDistance + " metrów");
            System.out.println("Obliczona odległość: " + calculatedDistance + " metrów");
            System.out.println("Czas: " + (i * timeUnit) + " sekund\n");
        }
    }
}

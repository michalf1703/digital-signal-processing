package com.example.zad1;

import com.example.zad1.Signals.ContinuousSignal;
import com.example.zad1.Signals.DiscreteSignal;
import com.example.zad1.Signals.ContinuousBasedDiscreteSignal;
import com.example.zad1.Signals.SinusoidalSignal;
import com.example.zad1.Signals.RectangularSignal;
import com.example.zad1.SignalsTask3.CorrelationSignal;
import com.example.zad1.SignalsTask3.OperationResultContinuousSignal;

public class Main {

    public static void main(String[] args) {
        // Parametry sygnału
        double probeSignalTerm = 1.0;
        double sampleRate = 100.0;
        int bufferLength = 10;
        double reportTerm = 0.5;
        double signalVelocity = 343.0;  // Prędkość dźwięku w powietrzu w m/s

        // Tworzenie sygnału sondującego
        ContinuousSignal probeSignal = new OperationResultContinuousSignal(
                new SinusoidalSignal(0.0, 0.0, 1.0, probeSignalTerm, sampleRate),
                new RectangularSignal(0.0, 0.0, 0.6, probeSignalTerm / 6 * 2, 0.3, sampleRate),
                (a, b) -> a + b);

        // Tworzenie sygnału zwrotnego z większym przesunięciem czasowym
        double feedbackDelay = 0.5; // 500 ms delay

// Tworzenie sygnału zwrotnego z większym przesunięciem czasowym
        int feedbackDelaySamples = (int) (feedbackDelay * sampleRate); // 0.5s delay in samples
        ContinuousSignal feedbackSignal = new OperationResultContinuousSignal(
                new SinusoidalSignal(feedbackDelaySamples, 0.0, 1.0, probeSignalTerm, sampleRate),
                new RectangularSignal(feedbackDelaySamples, 0.0, 0.6, probeSignalTerm / 6 * 2, 0.3, sampleRate),
                (a, b) -> a + b);
        // Konwersja do sygnałów dyskretnych
        DiscreteSignal discreteProbeSignal = new ContinuousBasedDiscreteSignal(0.0, bufferLength, sampleRate, probeSignal);
        DiscreteSignal discreteFeedbackSignal = new ContinuousBasedDiscreteSignal(0.0, bufferLength, sampleRate, feedbackSignal);

        // Wydruk długości sygnałów
        System.out.println("Długość dyskretnego sygnału sondującego: " + discreteProbeSignal.getNumberOfSamples());
        System.out.println("Długość dyskretnego sygnału sprzężenia zwrotnego: " + discreteFeedbackSignal.getNumberOfSamples());

        // Sprawdzenie przykładowych wartości sygnałów
        System.out.println("Przykładowe wartości sygnału sondującego:");
        for (int i = 0; i < 10; i++) {
            System.out.println("Index " + i + ": " + discreteProbeSignal.value(i));
        }

        System.out.println("Przykładowe wartości sygnału sprzężenia zwrotnego:");
        for (int i = 0; i < 10; i++) {
            System.out.println("Index " + i + ": " + discreteFeedbackSignal.value(i));
        }
// Obliczanie korelacji
        CorrelationSignal correlationSignal = new CorrelationSignal(discreteProbeSignal, discreteFeedbackSignal);

// Wydruk wyników korelacji
        correlationSignal.printCorrelation();

// Obliczanie odległości na podstawie korelacji
        int middleIndex = correlationSignal.getNumberOfSamples() / 2;
        int indexOfFirstMax = middleIndex;
        double maxCorrelationValue = correlationSignal.value(indexOfFirstMax);
        for (int i = middleIndex + 1; i < correlationSignal.getNumberOfSamples(); i++) {
            double currentValue = correlationSignal.value(i);
            if (currentValue > maxCorrelationValue) {
                indexOfFirstMax = i;
                maxCorrelationValue = currentValue;
            }
        }

// Wydruk opóźnienia
        System.out.println("sampleRate: " + sampleRate);
        System.out.println("correlationSignal.getNumberOfSamples() / 2: " + middleIndex);
        System.out.println("indexOfFirstMax: " + indexOfFirstMax);
        double delay = (indexOfFirstMax - middleIndex) / sampleRate;
        System.out.println("Opóźnienie: " + delay);

        double distance = delay * signalVelocity / 2.0;
        System.out.println("Calculated Distance: " + distance);
    }
}

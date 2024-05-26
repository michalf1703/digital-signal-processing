package com.example.zad1.simulation;


import com.example.zad1.Signals.*;
import com.example.zad1.SignalsTask3.CorrelationSignal;
import com.example.zad1.SignalsTask3.OperationResultContinuousSignal;

public class DistanceSensor {

    private final double probeSignalTerm;
    private final double sampleRate;
    private final int bufferLength;
    private final double reportTerm;
    private final double signalVelocity;

    private DiscreteSignal discreteProbeSignal;
    private DiscreteSignal discreteFeedbackSignal;
    private DiscreteSignal correlationSignal;
    private double lastCalculationTimestamp = -Double.MAX_VALUE;
    private double distance;

    public DistanceSensor(double probeSignalTerm, double sampleRate,
                          int bufferLength, double reportTerm, double signalVelocity) {
        this.probeSignalTerm = probeSignalTerm;
        this.sampleRate = sampleRate;
        this.bufferLength = bufferLength;
        this.reportTerm = reportTerm;
        this.signalVelocity = signalVelocity;
    }

    public final ContinuousSignal generateProbeSignal() {
        /* always return new independent copy of probe signal */
        return new SinusoidalSignal(0.0, probeSignalTerm, 1.0, probeSignalTerm,sampleRate);

    }

    public DiscreteSignal getDiscreteProbeSignal() {
        return discreteProbeSignal;
    }

    public DiscreteSignal getDiscreteFeedbackSignal() {
        return discreteFeedbackSignal;
    }

    public DiscreteSignal getCorrelationSignal() {
        return correlationSignal;
    }

    public double getDistance() {
        return distance;
    }

    public void update(ContinuousSignal feedbackSignal, double timestamp) {
        /* fill buffers */
        double rangeStart = timestamp - bufferLength / sampleRate;
        this.discreteProbeSignal = new ContinuousBasedDiscreteSignal(rangeStart, bufferLength,
                sampleRate, generateProbeSignal());
        this.discreteFeedbackSignal = new ContinuousBasedDiscreteSignal(rangeStart, bufferLength,
                sampleRate, feedbackSignal);

        /* check if it's time for distance calculation */
        if (timestamp - lastCalculationTimestamp >= reportTerm) {
            lastCalculationTimestamp = timestamp;
            calculateDistance();
        }
    }

    private void calculateDistance() {
        /* correlation */
        this.correlationSignal = new CorrelationSignal(discreteFeedbackSignal, discreteProbeSignal);

        /* find max */
        int indexOfFirstMax = correlationSignal.getNumberOfSamples() / 2;
        for (int i = indexOfFirstMax + 1; i < correlationSignal.getNumberOfSamples(); i++) {
            if (correlationSignal.value(i) > correlationSignal.value(indexOfFirstMax)) {
                indexOfFirstMax = i;
            }
        }

        /* calculate distance */
        double delay = (indexOfFirstMax - correlationSignal.getNumberOfSamples() / 2)
                / sampleRate;
        this.distance = delay * signalVelocity / 2.0;
    }
}

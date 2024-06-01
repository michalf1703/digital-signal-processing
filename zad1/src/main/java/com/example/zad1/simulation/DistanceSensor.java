package com.example.zad1.simulation;


import com.example.zad1.RadarSignals.ContinuousBasedDiscreteSignal2;
import com.example.zad1.RadarSignals.CorrelationSignal2;
import com.example.zad1.RadarSignals.DiscreteSignal2;
import com.example.zad1.Signals.*;
import com.example.zad1.SignalsTask3.OperationResultContinuousSignal;

public class DistanceSensor {

    private final double probeSignalTerm;
    private final double sampleRate;
    private final int bufferLength;
    private final double reportTerm;
    private final double signalVelocity;

    private DiscreteSignal2 discreteProbeSignal;
    private DiscreteSignal2 discreteFeedbackSignal;
    private DiscreteSignal2 correlationSignal;
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
        return new OperationResultContinuousSignal(
                new SinusoidalSignal(0.0, 0.0, 1.0, probeSignalTerm,sampleRate),
                new RectangularSignal(0.0, 0.0, 0.6, probeSignalTerm / 6 * 2, 0.3,sampleRate),
                (a, b) -> a + b);

    }
    public double getReportTerm() {
        return reportTerm;
    }
    public double getDistance() {
        return distance;
    }

    public void update(ContinuousSignal feedbackSignal, double timestamp) {
        double rangeStart = timestamp - bufferLength / sampleRate;
        this.discreteProbeSignal = new ContinuousBasedDiscreteSignal2(rangeStart, bufferLength,
                sampleRate, generateProbeSignal());
        this.discreteFeedbackSignal = new ContinuousBasedDiscreteSignal2(rangeStart, bufferLength,
                sampleRate, feedbackSignal);
        if (timestamp - lastCalculationTimestamp >= reportTerm) {
            lastCalculationTimestamp = timestamp;
            calculateDistance();
        }
       // System.out.println("Probe signal:" + discreteProbeSignal.value(0));
        //System.out.println("Feedback signal:" + discreteFeedbackSignal.value(0));
    }

    private void calculateDistance() {
        this.correlationSignal = new CorrelationSignal2(discreteFeedbackSignal, discreteProbeSignal);
        int indexOfFirstMax = correlationSignal.getNumberOfSamples()  / 2;
        for (int i = indexOfFirstMax + 1; i < correlationSignal.getNumberOfSamples(); i++) {
            if (correlationSignal.value(i) > correlationSignal.value(indexOfFirstMax)) {
                indexOfFirstMax = i;
            }
        }

        double delay = (indexOfFirstMax - correlationSignal.getNumberOfSamples() / 2)
                / sampleRate;
        this.distance = delay * signalVelocity / 2.0;
       // System.out.println("indexOfFirstMax: " + indexOfFirstMax);
       // System.out.println("Correlation: " + correlationSignal.value(indexOfFirstMax));
       // System.out.println("Distance: " + distance);
        //System.out.println("Delay: " + delay);
    }
}

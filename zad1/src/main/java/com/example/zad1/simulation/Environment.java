package com.example.zad1.simulation;

import com.example.zad1.Signals.ContinuousSignal;

public class Environment {

    private final double timeStep;
    private final double signalVelocity;
    private final double itemVelocity;
    private final DistanceSensor distanceSensor;
    private final int liczbaPomiarow;

    private double itemDistance;
    private double timestamp = 0.0;

    public Environment(double timeStep, double signalVelocity, double itemVelocity,
                       DistanceSensor distanceSensor, int liczbaPomiarow) {
        this.timeStep = timeStep;
        this.signalVelocity = signalVelocity;
        this.itemVelocity = itemVelocity;
        this.distanceSensor = distanceSensor;
        this.liczbaPomiarow = liczbaPomiarow;
    }

    public double getTimeStep() {
        return timeStep;
    }

    public double getSignalVelocity() {
        return signalVelocity;
    }

    public double getItemVelocity() {
        return itemVelocity;
    }

    public DistanceSensor getDistanceSensor() {
        return distanceSensor;
    }

    public double getItemDistance() {
        return itemDistance;
    }

    public double getTimestamp() {
        return timestamp;
    }

    public void step() {
        timestamp += timeStep;
        itemDistance += itemVelocity * timeStep;

        System.out.println("timestamp: " + timestamp);
        System.out.println("itemDistance: " + itemDistance);

        double delay = itemDistance / signalVelocity * 2.0;
        System.out.println("delay: " + delay);

        ContinuousSignal probeSignal = distanceSensor.generateProbeSignal();
        probeSignal.setRangeStart(probeSignal.getRangeStart() + delay);
        ContinuousSignal feedbackSignal = probeSignal;

        System.out.println("probeSignal: " + probeSignal.toString());
        System.out.println("feedbackSignal: " + feedbackSignal.toString());
        System.out.println("itemDistance: " + itemDistance);

        distanceSensor.update(feedbackSignal, timestamp);
    }
}

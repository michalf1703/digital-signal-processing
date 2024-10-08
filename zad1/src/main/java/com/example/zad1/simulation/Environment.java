package com.example.zad1.simulation;

import com.example.zad1.Signals.ContinuousSignal;

public class Environment {

    private final double timeStep;
    private final double signalVelocity;
    private final double itemVelocity;
    private final DistanceSensor distanceSensor;

    private double itemDistance;
    private double timestamp = 0.0;

    public Environment(double timeStep, double signalVelocity, double itemVelocity,
                       DistanceSensor distanceSensor, double startItemDistance) {
        this.timeStep = timeStep;
        this.signalVelocity = signalVelocity;
        this.itemVelocity = itemVelocity;
        this.distanceSensor = distanceSensor;
        this.itemDistance = startItemDistance;
    }

    public double getTimeStep() {
        return timeStep;
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
        double delay = itemDistance / signalVelocity * 2.0;
        ContinuousSignal probeSignal = distanceSensor.generateProbeSignal();
        probeSignal.setRangeStart(probeSignal.getRangeStart() + delay);
        ContinuousSignal feedbackSignal = probeSignal;
        distanceSensor.update(feedbackSignal, timestamp);
    }
}

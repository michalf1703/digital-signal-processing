package com.example.zad1;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Measurement {
    public int getMeasurementNumber() {
        return measurementNumber.get();
    }

    public SimpleIntegerProperty measurementNumberProperty() {
        return measurementNumber;
    }


    public SimpleDoubleProperty timeProperty() {
        return time;
    }


    public SimpleDoubleProperty calculatedDistanceProperty() {
        return calculatedDistance;
    }



    public SimpleDoubleProperty realDistanceProperty() {
        return realDistance;
    }


    public SimpleDoubleProperty differenceProperty() {
        return difference;
    }

    private final SimpleIntegerProperty measurementNumber;
    private final SimpleDoubleProperty time;
    private final SimpleDoubleProperty calculatedDistance;
    private final SimpleDoubleProperty realDistance;
    private final SimpleDoubleProperty difference;

    public Measurement(int measurementNumber, double time, double calculatedDistance, double realDistance, double difference) {
        this.measurementNumber = new SimpleIntegerProperty(measurementNumber);
        this.time = new SimpleDoubleProperty(time);
        this.calculatedDistance = new SimpleDoubleProperty(calculatedDistance);
        this.realDistance = new SimpleDoubleProperty(realDistance);
        this.difference = new SimpleDoubleProperty(difference);
    }
}
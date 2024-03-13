package com.example.zad1.Signals;

import com.example.zad1.Base.Data;
import com.example.zad1.Base.Range;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public abstract class Signal implements Serializable {
    protected final Data[] data;

    public Signal(int length) {
        this.data = new Data[length];
    }


    public List<Data> getData() {
        return Arrays.asList(data);
    }

    public List<Range> generateHistogram(int numberOfRanges) {
        final double min = Arrays.asList(data).stream()
                .mapToDouble(data -> data.getY()).min().getAsDouble();
        final double max = Arrays.asList(data).stream()
                .mapToDouble(data -> data.getY()).max().getAsDouble();
        final List<Range> ranges = new ArrayList<>();
        IntStream.range(0, numberOfRanges).forEach(i -> {
            double begin = min + (max - min) / numberOfRanges * i;
            double end = min + (max - min) / numberOfRanges * (i + 1);
            int quantity = (int) Arrays.asList(data).stream()
                    .filter(data -> data.getY() >= begin && data.getY() <= end)
                    .count();
            ranges.add(new Range(begin, end, quantity));
        });
        return ranges;
    }

    public double meanValue() {
        double sum = 0;
        for (int i = 0; i < data.length; i++) {
            sum += data[i].getY();
        }
        return sum / data.length;
    }

    public double absMeanValue() {
        double sum = 0;
        for (int i = 0; i < data.length; i++) {
            sum += Math.abs(data[i].getY());
        }
        return sum / data.length;
    }

    public double rmsValue() {
        return Math.sqrt(meanPowerValue());
    }

    public double varianceValue() {
        double mean = meanValue();
        double sum = 0;
        for (int i = 0; i < data.length; i++) {
            sum += Math.pow(data[i].getY() - mean, 2.0);
        }
        return sum / data.length;
    }

    public double meanPowerValue() {
        double sum = 0;
        for (int i = 0; i < data.length; i++) {
            sum += Math.pow(data[i].getY(), 2.0);
        }
        return sum / data.length;
    }

    public abstract void generate();
}
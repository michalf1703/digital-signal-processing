package com.example.zad1.Signals;
import com.example.zad1.Base.Data;
import com.example.zad1.Base.Range;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
public abstract class Signal implements Serializable {

    public final double rangeStart;
    private final double rangeLength;
    public final double sampleRate;

    public double getSampleRate() {
        return sampleRate;
    }
    public List<Data> getData() {
        return generateDiscreteRepresentation();
    }

    public Signal(double rangeStart, double rangeLength, double sampleRate) {
        this.rangeStart = rangeStart;
        this.rangeLength = rangeLength;
        this.sampleRate = sampleRate;
    }

    public double getRangeStart() {
        return rangeStart;
    }

    public double getRangeLength() {
        return rangeLength;
    }

    public abstract List<Data> generateDiscreteRepresentation();

    /* compute histogram */

    public static List<Range> generateHistogram(int numberOfRanges, List<Data> discreteRepresentation) {
        final double min = discreteRepresentation.stream().mapToDouble(data -> data.getY()).min().getAsDouble();
        final double max = discreteRepresentation.stream().mapToDouble(data -> data.getY()).max().getAsDouble();
        final List<Range> ranges = new ArrayList<>();
        IntStream.range(0, numberOfRanges).forEach(i -> {
            double begin = min + (max - min) / numberOfRanges * i;
            double end = min + (max - min) / numberOfRanges * (i + 1);
            int quantity =
                    (int) discreteRepresentation.stream().filter(data -> data.getY() >= begin && data.getY() <= end)
                            .count();
            ranges.add(new Range(begin, end, quantity));
        });
        return ranges;
    }

    /* compute params */

    public static double meanValue(List<Data> discreteRepresentation) {
        double sum = 0;
        for (int i = 0; i < discreteRepresentation.size(); i++) {
            sum += discreteRepresentation.get(i).getY();
        }
        return sum / discreteRepresentation.size();
    }

    public static double absMeanValue(List<Data> discreteRepresentation) {
        double sum = 0;
        for (int i = 0; i < discreteRepresentation.size(); i++) {
            sum += Math.abs(discreteRepresentation.get(i).getY());
        }
        return sum / discreteRepresentation.size();
    }

    public static double rmsValue(List<Data> discreteRepresentation) {
        return Math.sqrt(meanPowerValue(discreteRepresentation));
    }

    public static double varianceValue(List<Data> discreteRepresentation) {
        double mean = meanValue(discreteRepresentation);
        double sum = 0;
        for (int i = 0; i < discreteRepresentation.size(); i++) {
            sum += Math.pow(discreteRepresentation.get(i).getY() - mean, 2.0);
        }
        return sum / discreteRepresentation.size();
    }

    public static double meanPowerValue(List<Data> discreteRepresentation) {
        double sum = 0;
        for (int i = 0; i < discreteRepresentation.size(); i++) {
            sum += Math.pow(discreteRepresentation.get(i).getY(), 2.0);
        }
        return sum / discreteRepresentation.size();
    }


    public static double meanSquaredError(List<Data> result, List<Data> origin) {
        if (result.size() < origin.size()) {
            int sizeDifference = origin.size() - result.size();
            Data lastDataPoint = result.get(result.size() - 1);
            for (int i = 0; i < sizeDifference; i++) {
                result.add(new Data(lastDataPoint.getX(), lastDataPoint.getY()));
            }
        }
        double sum = 0.0;
        for (int i = 0; i < origin.size(); i++) {
            sum += Math.pow(result.get(i).getY() - origin.get(i).getY(), 2.0);
        }

        return sum / origin.size();
    }

    public static double signalToNoiseRatio(List<Data> result, List<Data> origin) {
        if (result.size() != origin.size()) {
            throw new OperationSignal.NotSameLengthException();
        }

        double resultSquaredSum = 0.0;
        double diffSquaredSum = 0.0;
        for (int i = 0; i < result.size(); i++) {
            resultSquaredSum += Math.pow(result.get(i).getY(), 2.0);
            diffSquaredSum += Math.pow(result.get(i).getY() - origin.get(i).getY(), 2.0);
        }

        return 10.0 * Math.log10(resultSquaredSum / diffSquaredSum);
    }

    public static double peakSignalToNoiseRatio(List<Data> result, List<Data> origin) {
        if (result.size() != origin.size()) {
            throw new OperationSignal.NotSameLengthException();
        }

        double resultMax = Double.MIN_VALUE;
        double diffSquaredSum = 0.0;
        for (int i = 0; i < result.size(); i++) {
            if (result.get(i).getY() > resultMax) {
                resultMax = result.get(i).getY();
            }
            diffSquaredSum += Math.pow(result.get(i).getY() - origin.get(i).getY(), 2.0);
        }

        return 10.0 * Math.log10(resultMax / (diffSquaredSum / result.size()));
    }

    public static double maximumDifference(List<Data> result, List<Data> origin) {
        if (result.size() != origin.size()) {
            throw new OperationSignal.NotSameLengthException();
        }

        double maxDiff = Double.MIN_VALUE;
        for (int i = 0; i < result.size(); i++) {
            double diff = Math.abs(result.get(i).getY() - origin.get(i).getY());
            if (diff > maxDiff) {
                maxDiff = diff;
            }
        }

        return maxDiff;
    }

    public static double effectiveNumberOfBits(List<Data> result, List<Data> origin) {
        return (signalToNoiseRatio(result, origin) - 1.76) / 6.02;
    }


    public abstract String getName();
}

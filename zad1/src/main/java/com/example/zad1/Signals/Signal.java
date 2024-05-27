package com.example.zad1.Signals;
import com.example.zad1.Base.Data;
import com.example.zad1.Base.Range;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
public abstract class Signal implements Serializable {

    public double rangeStart;
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
    public void setRangeStart(double rangeStart) {
        this.rangeStart = rangeStart;
    }

    public double getRangeStart() {
        return rangeStart;
    }

    public double getRangeLength() {
        return rangeLength;
    }

    public abstract List<Data> generateDiscreteRepresentation();


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
        int minLength = Math.min(result.size(), origin.size());

        double sumSquaredDifferences = 0.0;
        for (int i = 0; i < minLength; i++) {
            double difference = result.get(i).getY() - origin.get(i).getY();
            sumSquaredDifferences += difference * difference;
        }

        if (result.size() < origin.size()) {
            for (int i = minLength; i < origin.size(); i++) {
                double difference = -origin.get(i).getY();
                sumSquaredDifferences += difference * difference;
            }
        }
        else if (result.size() > origin.size()) {
            for (int i = minLength; i < result.size(); i++) {
                double difference = result.get(i).getY();
                sumSquaredDifferences += difference * difference;
            }
        }

        double mse = sumSquaredDifferences / origin.size();

        return mse;
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
        int minLength = Math.min(result.size(), origin.size());

        double resultMax = Double.MIN_VALUE;
        double diffSquaredSum = 0.0;
        for (int i = 0; i < minLength; i++) {
            double resultY = result.get(i).getY();
            double originY = origin.get(i).getY();

            if (resultY > resultMax) {
                resultMax = resultY;
            }

            diffSquaredSum += Math.pow(resultY - originY, 2.0);
        }

        if (result.size() < origin.size()) {
            for (int i = minLength; i < origin.size(); i++) {
                double originY = origin.get(i).getY();
                diffSquaredSum += originY * originY;
            }
        }

        else if (result.size() > origin.size()) {
            for (int i = minLength; i < result.size(); i++) {
                double resultY = result.get(i).getY();
                resultMax = Math.max(resultMax, resultY);
            }
        }

        double psnr = 10.0 * Math.log10(resultMax * resultMax / (diffSquaredSum / result.size()));

        return psnr;
    }

    public static double maximumDifference(List<Data> result, List<Data> origin) {
        int minLength = Math.min(result.size(), origin.size());

        double maxDiff = Double.MIN_VALUE;

        for (int i = 0; i < minLength; i++) {
            double diff = Math.abs(result.get(i).getY() - origin.get(i).getY());
            if (diff > maxDiff) {
                maxDiff = diff;
            }
        }

        if (result.size() > origin.size()) {
            for (int i = minLength; i < result.size(); i++) {
                double diff = Math.abs(result.get(i).getY());
                maxDiff = Math.max(maxDiff, diff);
            }
        }
        else if (result.size() < origin.size()) {
            for (int i = minLength; i < origin.size(); i++) {
                double diff = Math.abs(origin.get(i).getY());
                maxDiff = Math.max(maxDiff, diff);
            }
        }

        return maxDiff;
    }

    public static double effectiveNumberOfBits(List<Data> result, List<Data> origin) {
        return (signalToNoiseRatio(result, origin) - 1.76) / 6.02;
    }


    public abstract String getName();
}

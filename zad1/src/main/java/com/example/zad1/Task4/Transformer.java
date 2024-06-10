package com.example.zad1.Task4;

import com.example.zad1.Signals.DiscreteSignal;
import org.apache.commons.math3.complex.Complex;

public class Transformer {

    /* discrete fourier transform */
    private final DiscreteFourierTransform discreteFourierTransform;
    private final InSituFastFourierTransform inSituFastFourierTransform;


    /* discrete Walsh-Hadamrd transform */
    private final WalshHadamardTransform walshHadamardTransform;
    private final FastWalshHadamardTransform fastWalshHadamardTransform;


    public Transformer() {
        discreteFourierTransform = new DiscreteFourierTransform();
        inSituFastFourierTransform = new InSituFastFourierTransform();
        walshHadamardTransform = new WalshHadamardTransform();
        fastWalshHadamardTransform = new FastWalshHadamardTransform();
    }

    public DiscreteComplexSignal discreteFourierTransform(DiscreteSignal discreteSignal) {
        return transformRealSignalToComplexSignal(discreteSignal, discreteFourierTransform);
    }

    public DiscreteComplexSignal fastFourierTransformInSitu(DiscreteSignal discreteSignal) {
        return transformRealSignalToComplexSignal(discreteSignal, inSituFastFourierTransform);
    }



    public DiscreteSignal walshHadamardTransform(DiscreteSignal discreteSignal) {
        return transformRealSignalToRealSignal(discreteSignal, walshHadamardTransform);
    }

    public DiscreteSignal fastWalshHadamardTransform(DiscreteSignal discreteSignal) {
        return transformRealSignalToRealSignal(discreteSignal, fastWalshHadamardTransform);
    }

    private DiscreteSignal transformRealSignalToRealSignal(DiscreteSignal signal,
                                                           RealTransform realTransform) {
        System.out.println("Liczba probek:" + signal.getNumberOfSamples());
        double[] samples = new double[signal.getNumberOfSamples()];
        for (int i = 0; i < signal.getNumberOfSamples(); i++) {
            samples[i] = signal.value(i);
        }
        samples = realTransform.transform(samples);
        return new StaticDiscreteSignal(samples, signal.getSampleRate());
    }


    private DiscreteComplexSignal transformRealSignalToComplexSignal(DiscreteSignal signal,
                                                                     ComplexTransform complexTransform) {
        System.out.println("Liczba probek:" + signal.getNumberOfSamples());
        double[] samples = new double[signal.getNumberOfSamples()];
        for (int i = 0; i < signal.getNumberOfSamples(); i++) {
            samples[i] = signal.value(i);
        }
        Complex[] resultSamples = complexTransform.transform(samples);
        return new StaticDiscreteComplexSignal(resultSamples, signal.getSampleRate());
    }
}

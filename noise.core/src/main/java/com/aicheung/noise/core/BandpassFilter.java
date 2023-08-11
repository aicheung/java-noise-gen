package com.aicheung.noise.core;

import java.util.Arrays;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.jtransforms.fft.DoubleFFT_1D;

public class BandpassFilter {

    public double[] filter(double[] inputSignal, double sampleRate, double lowFreq, double highFreq) {
        return filter(inputSignal, sampleRate, new double[] {lowFreq}, new double[] {highFreq});
    }

    public double[] filter(double[] inputSignal, double sampleRate, double[] lowFreq, double[] highFreq) {
        if (lowFreq.length != highFreq.length) {
            throw new IllegalArgumentException("lowFreq and highFreq arrays must have the same length");
        }
        System.out.println(Arrays.toString(lowFreq));
        System.out.println(Arrays.toString(highFreq));
    
        DoubleFFT_1D fft = new DoubleFFT_1D(inputSignal.length);
        double[] fftData = new double[inputSignal.length * 2];
    
        System.arraycopy(inputSignal, 0, fftData, 0, inputSignal.length);
        fft.realForwardFull(fftData);
    
        // Initially mark all frequencies for zeroing
        boolean[] shouldZero = new boolean[fftData.length];
        Arrays.fill(shouldZero, true);

        // Unmark the desired frequency bands
        for (int band = 0; band < lowFreq.length; band++) {
            int lowBound = (int) (lowFreq[band] * fftData.length / sampleRate);
            int highBound = (int) (highFreq[band] * fftData.length / sampleRate);

            for (int i = lowBound; i <= highBound; i++) {
                shouldZero[i] = false;
                if(i + 1 < fftData.length && i == highBound) {
                    shouldZero[i + 1] = false;
                }
            }
        }

        // Zero out the marked frequencies
        for (int i = 0; i < fftData.length; i++) {
            if (shouldZero[i]) {
                fftData[i] = 0;
                //fftData[2 * i + 1] = 0;
            }
        }
    
        fft.complexInverse(fftData, true);
    
        double[] filteredSignal = new double[inputSignal.length];
        for (int i = 0; i < filteredSignal.length; i++) {
            filteredSignal[i] = fftData[i * 2];
        }
    
        return filteredSignal;
    }
}

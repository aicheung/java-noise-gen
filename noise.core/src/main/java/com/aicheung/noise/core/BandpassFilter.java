package com.aicheung.noise.core;

import org.jtransforms.fft.DoubleFFT_1D;

public class BandpassFilter {

    public double[] filter(double[] inputSignal, double sampleRate, double lowFreq, double highFreq) {
        DoubleFFT_1D fft = new DoubleFFT_1D(inputSignal.length);
        double[] fftData = new double[inputSignal.length * 2];
        
        System.arraycopy(inputSignal, 0, fftData, 0, inputSignal.length);
        fft.realForwardFull(fftData);
        
        int lowBound = (int) (lowFreq * fftData.length / sampleRate);
        int highBound = (int) (highFreq * fftData.length / sampleRate);
        
        // Zero out undesirable frequencies
        for (int i = 0; i < lowBound; i++) {
            fftData[i] = 0;
            fftData[fftData.length - 1 - i] = 0;
        }
        
        for (int i = highBound; i < fftData.length / 2; i++) {
            fftData[i] = 0;
            fftData[fftData.length - 1 - i] = 0;
        }

        fft.complexInverse(fftData, true);
        
        double[] filteredSignal = new double[inputSignal.length];
        for (int i = 0; i < filteredSignal.length; i++) {
            filteredSignal[i] = fftData[i * 2];
        }

        return filteredSignal;
    }
}

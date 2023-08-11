package com.aicheung.noise.core;

import javax.sound.sampled.*;
import java.util.Random;

public class NoiseGenerator {

    private static final float SAMPLE_RATE = 48000;
    private static final int AMPLITUDE = 32760;
    private static final int SAMPLE_SIZE = 16; // in bits
    private static final int CHANNELS = 1; // mono
    private static final boolean SIGNED = true;
    private static final boolean BIG_ENDIAN = false;
    private static final Random RANDOM = new Random();
    private static final float Q_FACTOR = 1.41f;

    private static BandpassFilter filter;

    public static void main(String[] args) throws LineUnavailableException {
        filter = new BandpassFilter();

        float[] frequencies = new float[] {100.0f, 1000.0f}; // can be taken from user input
        byte[] noise = generateFilteredNoise(frequencies);

        AudioFormat format = new AudioFormat(SAMPLE_RATE, SAMPLE_SIZE, CHANNELS, SIGNED, BIG_ENDIAN);
        SourceDataLine line = AudioSystem.getSourceDataLine(format);
        line.open(format);
        line.start();
        line.write(noise, 0, noise.length);
        line.drain();
        line.close();
    }

    public static byte[] generateFilteredNoise(float frequency) {
        return generateFilteredNoise(new float[] {frequency});
    }

    public static byte[] generateFilteredNoise(float[] frequencies) {
        int length = (int) SAMPLE_RATE * 15; // for example, 10 seconds of audio
        byte[] noiseData = new byte[length * 2]; // *2 for 16-bit samples

        // Generate white noise
        for (int i = 0; i < length; i++) {
            short sampleValue = (short) (RANDOM.nextFloat() * 2 * AMPLITUDE - AMPLITUDE);
            noiseData[i * 2] = (byte) (sampleValue & 0xFF);
            noiseData[i * 2 + 1] = (byte) ((sampleValue >> 8) & 0xFF);
        }

        double[] noise = ByteArrayConverter.byteArrayToDoubleArray(noiseData);
        double[] filtered = filter.filter(noise, SAMPLE_RATE, getLowFreqByQFactor(frequencies, Q_FACTOR), getHighFreqByQFactor(frequencies, Q_FACTOR));
        noiseData = ByteArrayConverter.doubleArrayToByteArray(filtered);
        return noiseData;
    }

    public static double[] getLowFreqByQFactor(float[] frequencies, float Q) {
        double[] lowFreq = new double[frequencies.length];
    
        for (int i = 0; i < frequencies.length; i++) {
            double bandwidth = frequencies[i] / Q;
            lowFreq[i] = frequencies[i] - bandwidth / 2;
        }
    
        return lowFreq;
    }
    
    public static double[] getHighFreqByQFactor(float[] frequencies, float Q) {
        double[] highFreq = new double[frequencies.length];
    
        for (int i = 0; i < frequencies.length; i++) {
            double bandwidth = frequencies[i] / Q;
            highFreq[i] = frequencies[i] + bandwidth / 2;
        }
    
        return highFreq;
    }
    
}
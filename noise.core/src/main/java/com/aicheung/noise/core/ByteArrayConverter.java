package com.aicheung.noise.core;

public class ByteArrayConverter {
    /**
     * Convert a byte array to a double array.
     * Assumes 16-bit PCM data.
     */
    public static double[] byteArrayToDoubleArray(byte[] byteArray) {
        double[] doubleArray = new double[byteArray.length / 2];
        
        for (int i = 0; i < doubleArray.length; i++) {
            // Convert two bytes into a 16-bit PCM sample and normalize it between -1.0 and 1.0
            doubleArray[i] = ((short) ((byteArray[i * 2] & 0xFF) | (byteArray[i * 2 + 1] << 8))) / 32767.0;
        }
        
        return doubleArray;
    }

    /**
     * Convert a double array back to byte array.
     * Converts the double samples into 16-bit PCM data.
     */
    public static byte[] doubleArrayToByteArray(double[] doubleArray) {
        byte[] byteArray = new byte[doubleArray.length * 2];
        
        for (int i = 0; i < doubleArray.length; i++) {
            // Convert the normalized double sample into a 16-bit PCM sample
            short sample = (short) (doubleArray[i] * 32767.0);
            
            byteArray[i * 2] = (byte) (sample & 0xFF);
            byteArray[i * 2 + 1] = (byte) ((sample >> 8) & 0xFF);
        }
        
        return byteArray;
    }
}

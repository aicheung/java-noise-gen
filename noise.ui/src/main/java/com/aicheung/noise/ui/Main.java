package com.aicheung.noise.ui;

import com.aicheung.noise.core.NoiseGenerator;

public class Main {
    public static void main(String[] args) {
        NoiseGenerator generator = new NoiseGenerator();
        float frequency = 100.0f; // Or get this from user input
        byte[] noise = generator.generateFilteredNoise(frequency);
        
        // Play the noise or provide more UI interactions
    }
}
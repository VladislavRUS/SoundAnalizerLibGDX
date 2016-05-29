package com.vlad.analyzer.sound;

import com.vlad.analyzer.model.Complex;
import com.vlad.analyzer.model.FFT;

import java.util.Map;

public class SpectrumData extends SoundData implements Runnable {
    private volatile Map<Float, Float> soundData;
    private static boolean WINDOWED = false;
    public static boolean ACTIVE = true;

    public SpectrumData(Map<Float, Float> map) {
        initDataLine();
        this.soundData = map;
    }

    @Override
    public void run() {
        dataLine.start();
        byte[] buffer = new byte[BUFFER_SIZE];
        double[] data = new double[BUFFER_SIZE];
        while (ACTIVE) {
            synchronized (soundData) {
                try {
                    soundData.wait();
                    int cnt = dataLine.read(buffer, 0, buffer.length);
                    if (cnt > 0) {
                        soundData.clear();
                        for (int i = 0; i < buffer.length; i++) {
                            data[i] = (WINDOWED) ? 0.5 * (1 - Math.cos((2 * Math.PI * buffer[i]) / (BUFFER_SIZE - 1))) : buffer[i];
                        }
                        Complex[] results = FFT.fft(data);
                        for (int i = 30; i < BUFFER_SIZE / 2; i++) {
                            float magnitude = Math.abs(Math.round(results[i].getReal()));
                            float freq = i * SAMPLE_RATE / BUFFER_SIZE;
                            if (freq > MAX_FREQ)
                                break;
                            soundData.put(freq, (WINDOWED) ? magnitude * 100 : magnitude);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

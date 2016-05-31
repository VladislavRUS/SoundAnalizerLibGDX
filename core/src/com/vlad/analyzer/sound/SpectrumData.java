package com.vlad.analyzer.sound;

import com.vlad.analyzer.model.Complex;
import com.vlad.analyzer.model.FFT;

import java.util.Map;

public class SpectrumData extends AbstractSoundData implements Runnable {
    private volatile Map<Float, Float> soundData;
    private boolean active;

    public SpectrumData(Map<Float, Float> map) {
        initDataLine();
        this.soundData = map;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public void run() {
        dataLine.start();
        byte[] buffer = new byte[SPECTRUM_DATA_SIZE];
        double[] data = new double[buffer.length];
        boolean WINDOWED = false;
        while (active) {
            synchronized (soundData) {
                try {
                    soundData.wait();
                    int cnt = dataLine.read(buffer, 0, buffer.length);
                    if (cnt > 0) {
                        soundData.clear();
                        for (int i = 0; i < buffer.length; i++) {
                            data[i] = (WINDOWED) ? 0.5 * (1 - Math.cos((2 * Math.PI * buffer[i]) / (buffer.length - 1))) : buffer[i];
                        }
                        Complex[] results = FFT.fft(data);
                        for (int i = 30; i < buffer.length / 2; i++) {
                            float magnitude = Math.abs(Math.round(results[i].getReal()));
                            float freq = i * SAMPLE_RATE / buffer.length;
                            if (freq > MAX_FREQ)
                                break;
                            soundData.put(freq, (WINDOWED) ? magnitude * 100 : magnitude / 10);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

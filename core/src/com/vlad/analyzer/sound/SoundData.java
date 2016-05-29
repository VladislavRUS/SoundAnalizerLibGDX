package com.vlad.analyzer.sound;

import javax.sound.sampled.*;

public abstract class SoundData {
    public static final int SAMPLE_RATE = 44100;
    public static final int BUFFER_SIZE = 4096;
    public static final int MAX_FREQ = 4000;

    protected AudioFormat getFormat() {
        float sampleRate = 44100;
        int sampleSizeInBits = 8;
        int channels = 1;
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, true, true);
    }

    protected TargetDataLine dataLine;

    public void initDataLine(){
        AudioFormat format = getFormat();
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        try {
            dataLine = (TargetDataLine) AudioSystem.getLine(info);
            dataLine.open(format);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void closeLine(){
        dataLine.close();
    }
}

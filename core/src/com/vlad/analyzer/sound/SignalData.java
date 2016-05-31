package com.vlad.analyzer.sound;

public class SignalData extends AbstractSoundData implements Runnable {
    private volatile byte[] soundBytes;
    private boolean active;

    public void setActive(boolean active) {
        this.active = active;
    }

    public SignalData(byte[] soundBytes) {
        initDataLine();
        this.soundBytes = soundBytes;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public void run() {
        byte[] buffer = new byte[soundBytes.length];
        dataLine.start();
        while (active) {
            int cnt = dataLine.read(buffer, 0, buffer.length);
            if (cnt > 0) {
                System.arraycopy(buffer, 0, soundBytes, 0, buffer.length);
            }
        }
    }
}

package com.vlad.analyzer.sound;

public class SignalData extends SoundData implements Runnable {
    private volatile byte[] soundBytes;
    public static boolean ACTIVE = true;
    //public static final int BUFFER_SIZE = 256;

    public SignalData(byte[] soundBytes){
        initDataLine();
        this.soundBytes = soundBytes;
    }

    @Override
    public void run() {
        byte[] buffer = new byte[BUFFER_SIZE];
        dataLine.start();
        while (ACTIVE){
            int cnt = dataLine.read(buffer, 0, buffer.length);
            if (cnt > 0) {
                System.arraycopy(buffer, 0, soundBytes, 0, buffer.length);
            }
        }
    }
}

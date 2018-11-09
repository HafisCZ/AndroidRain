package eu.mar21.rain.core.utils;

public class FrameCounter {

    private static final int SAMPLE_SIZE = 100;

    private final double frames[] = new double[SAMPLE_SIZE];

    private int frame = 0;
    private long lastTimestamp = 0;

    public void sample(long nano) {
        if (this.lastTimestamp > 0) {
            this.frame %= this.frames.length;
            this.frames[this.frame++] = 1_000_000_000D / (nano - this.lastTimestamp);
        }

        this.lastTimestamp = nano;
    }

    public double getInstantFPS() {
        return this.frames[this.frame % SAMPLE_SIZE];
    }

    public double getAverageFPS() {
        double fps = 0;
        for (int i = 0; i < SAMPLE_SIZE; i++) {
            fps += this.frames[i];
        }

        return fps / SAMPLE_SIZE;
    }

}

package eu.mar21.rain.core.utils;

public class Timer {

    // Params
    private final Action action;
    private final int rate;
    private int time;

    // Constructor
    public Timer(int rate, Action action) {
        this.rate = rate;
        this.action = action;
        this.time = 0;
    }

    // Methods
    public void tick() {
        if (++this.time > this.rate) {
            this.time = 0;
            this.action.accept();
        }
    }

}

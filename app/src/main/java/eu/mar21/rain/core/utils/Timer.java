package eu.mar21.rain.core.utils;

import eu.mar21.rain.core.utils.functional.Action;

public class Timer {

    // Params
    private final Action action;

    private int time;
    private int tick;

    // Constructor
    public Timer(Action action, int time) {
        this.action = action;
        this.time = time;
        this.tick = time;
    }

    // Methods
    public boolean tick() {
        if (--this.tick < 0) {
            this.tick = this.time;
            this.action.accept();

            return true;
        } else {
            return false;
        }
    }

    public void set(int time) {
        this.time = time;
    }

}

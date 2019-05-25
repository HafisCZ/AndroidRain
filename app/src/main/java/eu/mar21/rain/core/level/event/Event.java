package eu.mar21.rain.core.level.event;

import java.util.Random;

import eu.mar21.rain.core.level.Level;

public abstract class Event {

    // Default params
    protected static final Random RANDOM = new Random();

    // Params
    protected final Level level;
    private boolean removed;

    // Constructor
    protected Event(Level level) {
        this.level = level;
        this.removed = false;
    }

    // Methods
    public abstract void onStart();
    public abstract void onUpdate();
    public abstract void onRemoval();

    public void remove() {
        this.removed = true;
    }

    public boolean isRemoved() {
        return this.removed;
    }

}

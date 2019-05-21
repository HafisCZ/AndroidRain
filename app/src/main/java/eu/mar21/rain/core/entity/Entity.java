package eu.mar21.rain.core.entity;

import java.util.Random;

import eu.mar21.rain.core.level.Level;

public abstract class Entity {

    // Random
    protected static final Random RANDOM = new Random();

    // Params
    protected double x;
    protected double y;
    protected double dx;
    protected double dy;
    protected boolean removed;

    protected final double sx;
    protected final double sy;
    protected final Level level;

    // Constructor
    protected Entity(Level level, double x, double y, double sx, double sy) {
        this.level = level;
        this.x = x;
        this.y = y;
        this.sx = sx;
        this.sy = sy;

        this.dx = 0.0;
        this.dy = 0.0;
        this.removed = false;

    }

    // Methods
    public final double getX() {
        return this.x;
    }

    public final double getY() {
        return this.y;
    }

    public final double getCX() {
        return this.x + this.sx / 2.0;
    }

    public final double getCY() {
        return this.y + this.sy / 2.0;
    }

    public final double getSX() {
        return this.sx;
    }

    public final double getSY() {
        return this.sy;
    }

    public final double getDistance(double x, double y) {
        return Math.sqrt(Math.pow(x - getCX(), 2.0) + Math.pow(y - getCY(), 2.0));
    }

    public final double getDistance(Entity e) {
        return getDistance(e.x, e.y);
    }

    public final boolean isCollidingAABB(Entity e) {
        return (e.x + e.sx > this.x) && (this.x + this.sx > e.x) && (e.y + e.sy > this.y) && (this.y + this.sy > e.y);
    }

    public final boolean isRemoved() {
        return this.removed;
    }

    public final void remove() {
        this.removed = true;
    }

    public abstract void tick();

}

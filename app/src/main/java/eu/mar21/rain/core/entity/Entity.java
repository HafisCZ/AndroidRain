package eu.mar21.rain.core.entity;

import android.graphics.Canvas;

import java.util.Random;

import eu.mar21.rain.core.graphics.Drawable;
import eu.mar21.rain.core.graphics.sprite.Sprite;
import eu.mar21.rain.core.level.Level;

public abstract class Entity implements Drawable {

    protected static final Random RANDOM = new Random();

    protected final Sprite sprite;
    protected final double width;
    protected final double height;
    protected final double xOffset;
    protected final double yOffset;

    protected double x;
    protected double y;
    protected double dx;
    protected double dy;
    protected boolean dead;

    protected final Level level;

    protected Entity(double x, double y, double width, double height, Level level) {
        this(x, y, width, height, null, 0, 0, level);
    }

    protected Entity(double x, double y, double width, double height, Sprite sprite, double xOffset, double yOffset, Level level) {
        this.x = x;
        this.y = y;

        this.width = width;
        this.height = height;

        this.sprite = sprite;
        this.xOffset = 0;//xOffset;
        this.yOffset = 0;//yOffset;

        this.level = level;
    }

    @Override
    public void draw(Canvas c) {
        if (this.sprite != null) {
            this.sprite.draw(c, (int) (this.x), (int)(this.y));
        }
    }

    public final double getCenterX() {
        return this.x + this.width / 2;
    }

    public final double getCenterY() {
        return this.y + this.height / 2;
    }

    public final double getDistance(double x, double y) {
        return Math.sqrt(Math.pow(x - getCenterX(), 2) + Math.pow(y - getCenterY(), 2));
    }

    public final double getHeight() {
        return this.height;
    }

    public final double getWidth() {
        return this.width;
    }

    public final double getX() {
        return this.x;
    }

    public final double getY() {
        return this.y;
    }

    public final boolean isCollidingAABB(Entity entity) {
        final boolean a = entity.x + entity.width > this.x;
        final boolean b = this.x + this.width > entity.x;
        final boolean c = entity.y + entity.height > this.y;
        final boolean d = this.y + this.height > entity.y;
        return a && b && c && d;
    }

    public final boolean isDead() {
        return this.dead;
    }

    public final void kill() {
        this.dead = true;
    }

    public abstract void tick();
}

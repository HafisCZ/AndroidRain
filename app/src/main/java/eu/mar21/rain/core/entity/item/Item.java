package eu.mar21.rain.core.entity.item;

import eu.mar21.rain.core.level.Level;
import eu.mar21.rain.core.entity.Entity;
import eu.mar21.rain.core.graphics.sprite.Sprite;

public abstract class Item extends Entity {

    public static final double DEFAULT_SPEED_X = 0;
    public static final double DEFAULT_SPEED_Y = 4;

    public Item(double x, double y, double width, double height, Sprite sprite, double offsetX, double offsetY, double dx, double dy, Level level) {
        super(x, y, width, height, sprite, offsetX, offsetY, level);

        this.dx = dx;
        this.dy = dy;
    }

    protected Item(double x, double y, double width, double height, Sprite sprite, double offsetX, double offsetY, Level level) {
        this(x, y, width, height, sprite, offsetX, offsetY, DEFAULT_SPEED_X, DEFAULT_SPEED_Y, level);
    }

    @Override
    public final void tick() {
        this.x += this.dx;
        this.y += this.dy;

        if (this.y + this.height > 500) {
            kill();
        }

        if (this.level.isCollidingPlayerAABB(this)) {
            applyEffect();
            kill();
        }
    }

    public abstract void applyEffect();

}

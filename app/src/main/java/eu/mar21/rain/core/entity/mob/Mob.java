package eu.mar21.rain.core.entity.mob;

import android.graphics.Canvas;

import eu.mar21.rain.core.entity.Entity;
import eu.mar21.rain.core.graphics.Drawable;
import eu.mar21.rain.core.graphics.sprite.Sprite;
import eu.mar21.rain.core.level.Level;

public abstract class Mob extends Entity implements Drawable {

    // Params
    private final double ox;
    private final double oy;
    protected final Sprite sprite;

    // Constructor
    protected Mob(Level level, double x, double y, double sx, double sy, Sprite sprite, double ox, double oy) {
        super(level, x, y, sx, sy);

        this.sprite = sprite;
        this.ox = ox;
        this.oy = oy;
    }

    // Methods
    @Override
    public final void draw(Canvas c) {
        sprite.draw(c, this.x + this.ox, this.y + this.oy);
    }

}

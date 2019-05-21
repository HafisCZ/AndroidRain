package eu.mar21.rain.core.entity.item;

import android.graphics.Canvas;

import eu.mar21.rain.core.entity.Entity;
import eu.mar21.rain.core.graphics.Drawable;
import eu.mar21.rain.core.graphics.sprite.Sprite;
import eu.mar21.rain.core.level.Level;
import eu.mar21.rain.core.utils.Resources;

public abstract class Item extends Entity implements Drawable {

    // Default params
    protected static final double DEFAULT_DX = 0.0;
    protected static final double DEFAULT_DY = 4.0;

    // Params
    private final double ox;
    private final double oy;
    protected final Sprite sprite;

    // Constructor
    protected Item(Level level, double x, double y, double sx, double sy, Sprite sprite, double ox, double oy, double dx, double dy) {
        super(level, x, y, sx, sy);

        this.sprite = sprite;
        this.ox = ox;
        this.oy = oy;

        this.dx = dx * Resources.RES_MULTX;
        this.dy = dy * Resources.RES_MULTY;
    }

    //Methods
    @Override
    public final void tick() {
        this.x += this.dx;
        this.y += this.dy;

        if (this.y + this.sy > Resources.SCREEN_HEIGHT) {
            remove();
        }

        if (this.level.isCollidingPlayerAABB(this)) {
            effect();
            remove();
        }
    }

    @Override
    public final void draw(Canvas c) {
        sprite.draw(c, this.x + this.ox, this.y + this.oy);
    }

    public abstract void effect();

}

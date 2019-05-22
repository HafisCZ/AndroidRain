package eu.mar21.rain.core.entity.particle;

import android.graphics.Canvas;
import android.graphics.Paint;

import eu.mar21.rain.core.level.Level;
import eu.mar21.rain.core.utils.Resources;

public class RainParticle extends Particle {

    // Params
    private final Paint color;

    // Constructor
    public RainParticle(Level level, double x, double y, double sx, double sy, double dx, double dy) {
        super(level, x, y, sx, sy);

        this.dx = dx;
        this.dy = dy;
        this.color = RANDOM.nextBoolean() ? Resources.PAINT_FF6495ED : Resources.PAINT_FFADD8E6;
    }

    // Methods
    @Override
    public void draw(Canvas c) {
        c.drawRect((float) this.x, (float) this.y, (float) (this.x + this.sx), (float) (this.y + this.sy), this.color);
    }

    @Override
    public void tick() {
        this.x += this.dx;
        this.y += this.dy;

        if (this.y > Resources.SCREEN_HEIGHT || (this.level.getPlayer() != null && this.level.getPlayer().isCollidingAABB(this))) {
            remove();
        }
    }
}

package eu.mar21.rain.core.entity.particle;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import eu.mar21.rain.core.level.Level;
import eu.mar21.rain.core.utils.Resources;

public class RainParticle extends Particle {

    // Default params
    private static final Paint COLOR_1 = new Paint();
    private static final Paint COLOR_2 = new Paint();
    static {
        COLOR_1.setColor(Color.argb(255, 100, 149, 237));
        COLOR_2.setColor(Color.argb(255, 173, 216, 230));
    }

    // Params
    private final Paint color;

    // Constructor
    public RainParticle(Level level, double x, double y, double sx, double sy, double dx, double dy) {
        super(level, x, y, sx, sy);

        this.dx = dx;
        this.dy = dy;
        this.color = RANDOM.nextBoolean() ? COLOR_1 : COLOR_2;
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

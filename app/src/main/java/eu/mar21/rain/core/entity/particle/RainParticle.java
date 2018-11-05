package eu.mar21.rain.core.entity.particle;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import eu.mar21.rain.core.Level.Level;
import eu.mar21.rain.core.utils.Resources;

public class RainParticle extends Particle {

    private static final Paint COLOR_1 = new Paint();
    private static final Paint COLOR_2 = new Paint();
    static {
        COLOR_1.setColor(Color.argb(255, 100, 149, 237));
        COLOR_2.setColor(Color.argb(255, 173, 216, 230));
    }

    private final Paint color;

    public RainParticle(double x, double y, double width, double height, double dx, double dy, Level level) {
        super(x, y, width, height, level);

        this.dx = dx;
        this.dy = dy;

        this.color = RANDOM.nextBoolean() ? COLOR_1 : COLOR_2;
    }

    @Override
    public void draw(Canvas c) {
        c.drawRect((int) this.x, (int) this.y, (int) (this.x + this.width), (int) (this.y + this.height), this.color);
    }

    @Override
    public void tick() {
        this.x += this.dx;
        this.y += this.dy;

        if (this.level.getPlayer() != null) {
            if (this.level.getPlayer().isCollidingAABB(this)) {
                kill();
            }
        }

        if (this.y > Resources.SCREEN_HEIGHT) {
            kill();
        }
    }
}

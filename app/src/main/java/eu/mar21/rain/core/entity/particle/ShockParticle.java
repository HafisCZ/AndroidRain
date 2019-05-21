package eu.mar21.rain.core.entity.particle;

import android.graphics.Canvas;
import android.graphics.Paint;

import eu.mar21.rain.core.entity.Entity;
import eu.mar21.rain.core.entity.mob.Acid;
import eu.mar21.rain.core.level.Level;
import eu.mar21.rain.core.utils.Resources;

public class ShockParticle extends Particle {

    // Default params
    private static final double ARC_RAD_INC = 8.0;
    private static final Paint COLOR = new Paint();
    static {
        COLOR.setColor(0xFFF0F8FF);
        COLOR.setStyle(Paint.Style.STROKE);
    }

    // Params
    private double radius = 0.0;

    // Constructor
    public ShockParticle(Level level, double x, double y, double sx, double sy) {
        super(level, x, y, sx, sy);
    }

    // Methods
    @Override
    public void draw(Canvas c) {
        c.drawCircle((float) this.x, (float) this.y, (float) this.radius, COLOR);
    }

    @Override
    public void tick() {
        this.radius += ARC_RAD_INC * Resources.RES_MULTX;

        if (this.radius > Resources.SCREEN_WIDTH) {
            remove();
        }

        for (Entity e : this.level.getMobs()) {
            if (e instanceof Acid) {
                if (e.getDistance(this) <= this.radius) {
                    e.remove();
                }
            }
        }
    }

}

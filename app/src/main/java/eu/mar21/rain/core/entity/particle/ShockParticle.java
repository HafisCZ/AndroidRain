package eu.mar21.rain.core.entity.particle;

import android.graphics.Canvas;

import eu.mar21.rain.core.entity.Entity;
import eu.mar21.rain.core.entity.mob.Acid;
import eu.mar21.rain.core.level.Level;
import eu.mar21.rain.core.utils.Resources;

public class ShockParticle extends Particle {

    // Default params
    private static final double ARC_RAD_INC = 8.0;

    // Params
    private double radius = 0.0;

    // Constructor
    public ShockParticle(Level level, double x, double y) {
        super(level, x, y, 0, 0);
    }

    // Methods
    @Override
    public void draw(Canvas c) {
        c.drawCircle((float) this.x, (float) this.y, (float) this.radius, Resources.PAINT_FFF0F8FF_STROKE);
    }

    @Override
    public void tick() {
        this.radius += ARC_RAD_INC * Resources.MX;

        if (this.radius > Resources.WIDTH) {
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

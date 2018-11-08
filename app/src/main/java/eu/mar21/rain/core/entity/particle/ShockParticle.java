package eu.mar21.rain.core.entity.particle;

import android.graphics.Canvas;
import android.graphics.Paint;

import eu.mar21.rain.core.entity.Entity;
import eu.mar21.rain.core.entity.mob.Acid;
import eu.mar21.rain.core.level.Level;
import eu.mar21.rain.core.utils.Resources;

public class ShockParticle extends Particle {

    public static final double ARC_RADIUS_INCREMENT = 8;
    public static final double ARC_ANGLE = 180;

    private static final Paint COLOR = new Paint();
    static {
        COLOR.setColor(0xFFF0F8FF);
        COLOR.setStyle(Paint.Style.STROKE);
    }

    private double radius = 0;

    public ShockParticle(double x, double y, double width, double height, Level level) {
        super(x, y, width, height, level);
    }

    @Override
    public void draw(Canvas c) {
        c.drawCircle((float) this.x, (float) this.y, (float) this.radius, COLOR);
    }

    @Override
    public void tick() {
        this.radius += ARC_RADIUS_INCREMENT;
        if (this.radius > Resources.SCREEN_WIDTH) {
            kill();
        }

        for (Entity m : this.level.getMobs()) {
            if (m instanceof Acid) {
                if (m.getDistance(this.x, this.y) <= this.radius) {
                    m.kill();
                }
            }
        }
    }

}

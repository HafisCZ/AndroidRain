package eu.mar21.rain.core.entity.particle;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

import eu.mar21.rain.core.entity.mob.LightningPole;
import eu.mar21.rain.core.level.Level;
import eu.mar21.rain.core.utils.Resources;

public class FlashParticle extends Particle {

    // Params
    private int alpha;
    private int decay;
    private boolean topole;

    private List<Float[]> points;

    // Constructor
    public FlashParticle(Level level, double x, double y, int decay) {
        super(level, 0, 0, Resources.SCREEN_WIDTH, Resources.SCREEN_HEIGHT);

        this.alpha = 155;
        this.decay = decay;

        this.points = new ArrayList<>();

        double lx = x;
        double ly = y;

        LightningPole pole = this.level.getPole();
        if (pole != null) {
            pole.strike();
            this.topole = true;

            ly = pole.getY();
            lx = pole.getCX();
        } else {
            this.topole = false;
        }

        while (ly > 0) {
            points.add(new Float[]{ (float) lx, (float) ly });

            lx += (RANDOM.nextBoolean() ? 1 : -1) * RANDOM.nextDouble() * Resources.SCREEN_WIDTH / 15.0;
            ly -= Resources.SCREEN_HEIGHT / 15.0 + RANDOM.nextDouble() * Resources.SCREEN_HEIGHT / 2.0;
        }

        points.add(new Float[]{ (float) lx, 0.0f });
    }

    // Methods
    @Override
    public void tick() {
        if (this.alpha == 155) {
            if (!this.topole && this.level.getPlayer().isCollidingAABB(this.points.get(0)[0], this.level.getPlayer().getCY())) {
                this.level.getData().applyLightning();
            }
        }

        this.alpha -= this.decay;
        if (this.alpha < 1) {
            this.alpha = 0;
            remove();
        }
    }

    @Override
    public void draw(Canvas c) {
        Resources.PAINT_LIGHTNING.setAlpha(this.alpha);
        c.drawRect(0, 0, (float) Resources.SCREEN_WIDTH, (float) Resources.SCREEN_HEIGHT, Resources.PAINT_LIGHTNING);

        Resources.PAINT_LIGHTNING.setAlpha(200);
        for (int i = 1; i < this.points.size(); i++) {
            c.drawLine(this.points.get(i - 1)[0], this.points.get(i - 1)[1], this.points.get(i)[0], this.points.get(i)[1], Resources.PAINT_LIGHTNING);
        }
    }

}

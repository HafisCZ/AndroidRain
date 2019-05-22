package eu.mar21.rain.core.entity.particle;

import android.graphics.Canvas;

import eu.mar21.rain.core.level.Level;
import eu.mar21.rain.core.utils.Resources;

public class FlashParticle extends Particle {

    // Params
    private int alpha;
    private int decay;

    // Constructor
    public FlashParticle(Level level, int decay) {
        super(level, 0, 0, Resources.SCREEN_WIDTH, Resources.SCREEN_HEIGHT);

        this.alpha = 155;
        this.decay = decay;
    }

    // Methods
    @Override
    public void tick() {
        this.alpha -= this.decay;

        if (this.alpha < 1) {
            remove();
        }
    }

    @Override
    public void draw(Canvas c) {
        Resources.PAINT_LIGHTNING.setAlpha(this.alpha);
        c.drawRect(0, 0, (float) Resources.SCREEN_WIDTH, (float) Resources.SCREEN_HEIGHT, Resources.PAINT_LIGHTNING);
    }

}

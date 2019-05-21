package eu.mar21.rain.core.entity.particle;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import eu.mar21.rain.core.level.Level;
import eu.mar21.rain.core.utils.Resources;

public class AcidParticle extends Particle {

    // Default params
    private static final double DEFAULT_DX_STEP = 0.2;
    private static final double DEFAULT_DY_STEP = 0.5;
    private static final int DESPAWN_MIN = 10;
    private static final int DESPAWN_MAX = 30;
    private static final Paint COLOR_1 = new Paint();
    private static final Paint COLOR_2 = new Paint();
    static {
        COLOR_1.setColor(Color.argb(255, 124, 252, 0));
        COLOR_2.setColor(Color.argb(255, 173, 255, 47));
    }

    // Params
    private final Paint color;
    private int despawn;
    private boolean despawnEnabled;

    // Constructor
    public AcidParticle(Level level, double x, double y, double sx, double sy, double dx, double dy) {
        super(level, x, y, sx, sy);

        this.dx = dx;
        this.dy = dy;
        this.color = RANDOM.nextBoolean() ? COLOR_1 : COLOR_2;
        this.despawn = DESPAWN_MIN + RANDOM.nextInt(DESPAWN_MAX - DESPAWN_MIN + 1);
        this.despawnEnabled = false;
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

        if (this.dx > 0) {
            this.dx = Math.max(0, this.dx - DEFAULT_DX_STEP * Resources.RES_MULTX);
        } else if (this.dx < 0) {
            this.dx = Math.min(0, this.dx + DEFAULT_DX_STEP * Resources.RES_MULTX);
        }

        if (this.x < 0) {
            this.x = 0;
            this.dx *= -1;
        } else if (this.x + this.sx > Resources.SCREEN_WIDTH) {
            this.x = Resources.SCREEN_WIDTH - this.sx;
            this.dx *= -1;
        }

        if (this.y + this.sy > Resources.SCREEN_HEIGHT) {
            this.y = Resources.SCREEN_HEIGHT - this.sy;
            this.despawnEnabled = true;
            this.dy = 0;
        } else {
            this.dy += DEFAULT_DY_STEP * Resources.RES_MULTY;
        }

        if (this.despawnEnabled && --this.despawn <= 0) {
            remove();
        }
    }
}

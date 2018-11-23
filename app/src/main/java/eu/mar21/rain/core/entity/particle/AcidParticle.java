package eu.mar21.rain.core.entity.particle;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import eu.mar21.rain.core.level.Level;
import eu.mar21.rain.core.utils.Resources;

public class AcidParticle extends Particle {

    public static final double SPEED_X_INCREMENT = 0.2;
    public static final double SPEED_Y_INCREMENT = 0.5;
    public static final int TICKS_DESPAWN_MIN = 10;
    public static final int TICKS_DESPAWN_MAX = 30;

    private static final Paint COLOR_1 = new Paint();
    private static final Paint COLOR_2 = new Paint();
    static {
        COLOR_1.setColor(Color.argb(255, 124, 252, 0));
        COLOR_2.setColor(Color.argb(255, 173, 255, 47));
    }

    private final Paint color;

    private int despawnTicks = TICKS_DESPAWN_MIN + RANDOM.nextInt(TICKS_DESPAWN_MAX - TICKS_DESPAWN_MIN + 1);
    private boolean despawnActive = false;

    public AcidParticle(double x, double y, double width, double height, double dx, double dy, Level level) {
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

        if (this.dx > 0) {
            this.dx = Math.max(0, this.dx - SPEED_X_INCREMENT * Resources.RES_MULTX);
        } else if (this.dx < 0) {
            this.dx = Math.min(0, this.dx + SPEED_X_INCREMENT * Resources.RES_MULTX);
        }

        if (this.x < 0) {
            this.x = 0;
            this.dx *= -1;
        } else if (this.x + this.width > Resources.SCREEN_WIDTH) {
            this.x = Resources.SCREEN_WIDTH - this.width;
            this.dx *= -1;
        }

        if (this.y + this.height > Resources.SCREEN_HEIGHT) {
            this.y = Resources.SCREEN_HEIGHT - this.height;
            this.despawnActive = true;
            this.dy = 0;
        } else {
            this.dy += SPEED_Y_INCREMENT * Resources.RES_MULTY;
        }

        if (this.despawnActive) {
            this.despawnTicks--;

            if (this.despawnTicks <= 0) {
                kill();
            }
        }
    }
}

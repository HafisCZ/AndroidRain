package eu.mar21.rain.core.entity.mob;

import eu.mar21.rain.core.entity.particle.AcidParticle;
import eu.mar21.rain.core.graphics.sprite.AnimatedSprite;
import eu.mar21.rain.core.level.Level;
import eu.mar21.rain.core.utils.Resources;

public class Acid extends Mob {

    // Default params
    private static final double DEFAULT_DX = 0.0;
    private static final double DEFAULT_DY = 10.0;
    private static final int PARTICLE_CNT = 5;
    private static final int ANIM_DELTA = 10;
    private static final int[] ANIM_GRP0 = { 0, 1, 2, 3 };

    // Constructor
    public Acid(Level level, double x, double y, double dx, double dy) {
        super(level, x, y, Resources.ACID[0].getWidth(), Resources.ACID[0].getHeight() / 2.0, new AnimatedSprite(Resources.ACID, RANDOM.nextInt(ANIM_DELTA) + ANIM_DELTA, ANIM_GRP0), 0, -Resources.ACID[0].getHeight() / 2.0);
        ((AnimatedSprite) this.sprite).play(0);

        this.dx = dx * Resources.RES_MULTX;
        this.dy = dy * Resources.RES_MULTY;
    }

    public Acid(Level level, double x, double y) {
        this(level, x, y, DEFAULT_DX, DEFAULT_DY);
    }

    // Methods
    @Override
    public final void tick() {
        this.x += this.dx;
        this.y += this.dy;

        if (this.y + this.sy >= Resources.SCREEN_HEIGHT) {
            this.y = Resources.SCREEN_HEIGHT - this.sy;

            remove();
            spawnParticles(0.0);
        }

        if (this.level.isCollidingPlayerAABB(this)) {
            this.level.getData().damage();

            this.y -= this.sy;
            spawnParticles(-1.0);

            remove();
        }

        ((AnimatedSprite) this.sprite).tick();
    }

    private void spawnParticles(double ySpeed) {
        for (int i = 0; i < PARTICLE_CNT; i++) {
            double particleSize = RANDOM.nextInt(5) + 1;
            double particleXSpeed = RANDOM.nextInt(5) - 2.5;
            this.level.addLater(new AcidParticle(this.level, this.x, this.y + this.sy - particleSize, particleSize, particleSize, particleXSpeed, ySpeed));
        }
    }

}

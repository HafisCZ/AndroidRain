package eu.mar21.rain.core.entity.mob;

import eu.mar21.rain.core.entity.particle.AcidParticle;
import eu.mar21.rain.core.graphics.sprite.AnimatedSprite;
import eu.mar21.rain.core.level.Level;
import eu.mar21.rain.core.level.data.Skill;
import eu.mar21.rain.core.level.data.Statistics;
import eu.mar21.rain.core.utils.Resources;

public class Acid extends Mob {

    public static final double SPEED_X_DEFAULT = 0;
    public static final double SPEED_Y_DEFAULT = 10;

    public static final int PARTICLE_COUNT = 5;

    public static final int ANIMATION_DELTA = 10;
    public static final int ANIMATION_FRAMEGROUP_0[] = { 0, 1, 2, 3 };

    public Acid(double x, double y, double dx, double dy, Level level) {
        super(x, y, Resources.ACID[0].getWidth(), Resources.ACID[0].getHeight() / 2, new AnimatedSprite(Resources.ACID, RANDOM.nextInt(ANIMATION_DELTA) + ANIMATION_DELTA, ANIMATION_FRAMEGROUP_0), 0, -Resources.ACID[0].getHeight() / 2, level);
        ((AnimatedSprite) this.sprite).play(0);

        this.dx = dx;
        this.dy = dy;
    }

    public Acid(double x, double y, Level level) {
        this(x, y, SPEED_X_DEFAULT, SPEED_Y_DEFAULT, level);
    }

    @Override
    public final void tick() {
        this.x += this.dx;
        this.y += this.dy;

        if (this.y + this.height >= Resources.SCREEN_HEIGHT) {
            this.y = Resources.SCREEN_HEIGHT - this.height;

            kill();
            spawnParticles(PARTICLE_COUNT, 0);
        }

        if (this.level.isCollidingPlayerAABB(this)) {
            this.level.getData().damage();

            this.y -= this.height;

            if (this.level.getData().getPlayerHealth() > 0) {

                if (Statistics.PLAYER_UPGRADE_DMG_SHOCKWAVE.get() >= 1) {
                    Skill.SHOCKWAVE.applyEffect(getCenterX(), Resources.SCREEN_HEIGHT, this.level);
                }

                spawnParticles(PARTICLE_COUNT, -1);
            }

            kill();
        }

        ((AnimatedSprite) this.sprite).tick();
    }

    protected void spawnParticles(int amount, double ySpeed) {
        for (int i = 0; i < amount; i++) {
            double particleSize = RANDOM.nextInt(5) + 1;
            double particleXSpeed = RANDOM.nextInt(5) - 2.5;
            this.level.addLater(new AcidParticle(this.x, this.y + this.height - particleSize, particleSize, particleSize, particleXSpeed, ySpeed, this.level));
        }
    }

}

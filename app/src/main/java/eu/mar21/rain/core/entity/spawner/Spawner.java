package eu.mar21.rain.core.entity.spawner;

import eu.mar21.rain.core.Level.Level;
import eu.mar21.rain.core.entity.Entity;

public abstract class Spawner extends Entity {

    protected final int rate;
    protected final int variation;
    protected final int count;

    protected int frameCount = 0;
    protected int frameLimit;

    protected Spawner(double x, double y, double width, double height, Level level, int rate, int variation, int count) {
        super(x, y, width, height, level);

        this.count = count;

        this.variation = variation;
        this.rate = rate;

        this.frameLimit = rate;
    }

    @Override
    public final void tick() {
        if (this.frameCount++ >= this.frameLimit) {
            this.frameCount = 0;
            this.frameLimit = this.rate + (this.variation > 1 ? RANDOM.nextInt(this.variation) : 0);

            if (this.level != null) {
                for (int i = 0; i < this.count; i++) {
                    spawn();
                }
            }
        }
    }

    protected double getRandomX() {
        return this.x + (this.width == 0 ? 0 : RANDOM.nextInt((int) this.width));
    }

    protected double getRandomY() {
        return this.y + (this.height == 0 ? 0 : RANDOM.nextInt((int) this.height));
    }

    public abstract void spawn();

}

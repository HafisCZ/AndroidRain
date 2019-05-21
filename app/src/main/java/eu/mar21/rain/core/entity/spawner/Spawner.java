package eu.mar21.rain.core.entity.spawner;

import eu.mar21.rain.core.entity.Entity;
import eu.mar21.rain.core.level.Level;

public abstract class Spawner extends Entity {

    // Params
    private final int rate;
    private final int rnd;
    private final int count;
    private int frame;
    private int frameCount;

    // Constructor
    public Spawner(Level level, double x, double y, double sx, double sy, int rate, int rnd, int count) {
        super(level, x, y, sx, sy);

        this.rate = rate;
        this.rnd = rnd;
        this.count = count;

        this.frame = 0;
        this.frameCount = rate;
    }

    // Methods
    @Override
    public final void tick() {
        if (this.frame++ >= this.frameCount) {
            this.frame = 0;
            this.frameCount = this.rate + (this.rnd > 1 ? RANDOM.nextInt(this.rnd) : 0);

            if (this.level != null) {
                for (int i = 0; i < this.count; i++) {
                    spawn();
                }
            }
        }
    }

    protected double getRndX() {
        return this.x + (this.sx == 0 ? 0 : RANDOM.nextInt((int) this.sx));
    }

    protected double getRndY() {
        return this.y + (this.sy == 0 ? 0 : RANDOM.nextInt((int) this.sy));
    }

    public abstract void spawn();

}

package eu.mar21.rain.core.entity.spawner;

import eu.mar21.rain.core.entity.particle.RainParticle;
import eu.mar21.rain.core.level.Level;

public class RainSpawner extends Spawner {

    // Default params
    private static final int WIDTH = 1;
    private static final int HEIGHT_MIN = 10;
    private static final int HEIGHT_MAX = 40;
    private static final int SPEED_X = 0;
    private static final int SPEED_Y_MIN = 10;
    private static final int SPEED_Y_MAX = 40;

    // Constructor
    public RainSpawner(Level level, double x, double y, double sx, double sy, int rate, int rnd, int count) {
        super(level, x, y, sx, sy, rate, rnd, count);
    }

    // Methods
    @Override
    public void spawn() {
        this.level.add(new RainParticle(
               this.level, getRndX(), getRndY(),
                WIDTH,HEIGHT_MIN + RANDOM.nextInt(HEIGHT_MAX - HEIGHT_MIN + 1),
                SPEED_X, SPEED_Y_MIN + RANDOM.nextInt(SPEED_Y_MAX - SPEED_Y_MIN + 1)
            )
        );
    }

}

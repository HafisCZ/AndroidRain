package eu.mar21.rain.core.entity.mob;

import android.util.Log;

import eu.mar21.rain.core.graphics.sprite.AnimatedSprite;
import eu.mar21.rain.core.level.Level;
import eu.mar21.rain.core.utils.Resources;

import static eu.mar21.rain.core.utils.Input.LEFT;
import static eu.mar21.rain.core.utils.Input.RIGHT;
import static eu.mar21.rain.core.utils.Input.UP;

public class Player extends Mob {

    public static final double WIDTH = 94;
    public static final double HEIGHT = 130;

    public static final double SPEED_X_LIMIT = 20;
    public static final double SPEED_Y_LIMIT = -10;
    public static final double SPEED_X_INCREMENT = 2.5;
    public static final double SPEED_Y_INCREMENT = 0.4;
    public static final double SPRITE_X_OFFSET = -8;
    public static final double SPRITE_Y_OFFSET = -2;

    public static final int ANIMATION_DELTA = 8;
    public static final int ANIMATION_FRAMEGROUP_0[] = { 0 };
    public static final int ANIMATION_FRAMEGROUP_1[] = { 1, 2, 3, 4 };
    public static final int ANIMATION_FRAMEGROUP_2[] = { 5, 6, 7, 8 };

    private final double speed;
    private boolean jump = true;

    public Player(double x, double y, Level level) {
        super(x, y, WIDTH, HEIGHT, new AnimatedSprite(Resources.PLAYER, ANIMATION_DELTA, ANIMATION_FRAMEGROUP_0, ANIMATION_FRAMEGROUP_1, ANIMATION_FRAMEGROUP_2), SPRITE_X_OFFSET, SPRITE_Y_OFFSET, level);

        this.speed = SPEED_X_INCREMENT;
    }

    @Override
    public void tick() {
        if (this.level.getInput().isHeld(RIGHT) && !this.level.getInput().isHeld(LEFT)) {
            this.dx = Math.min(this.dx + this.speed, SPEED_X_LIMIT);
        }

        if (this.level.getInput().isHeld(LEFT) && !this.level.getInput().isHeld(RIGHT)) {
            this.dx = Math.max(this.dx - this.speed, -SPEED_X_LIMIT);
        }

        if (!this.level.getInput().isHeld(LEFT) && !this.level.getInput().isHeld(RIGHT)) {
            if (this.dx > 0) {
                this.dx = Math.max(this.dx - this.speed, 0);
            } else if (this.dx < 0) {
                this.dx = Math.min(this.dx + this.speed, 0);
            }
        }

        if (this.level.getInput().isHeld(UP)) {
            if (!this.jump) {
                this.jump = true;
                this.dy = SPEED_Y_LIMIT;
            }
        }

        /*
        if (this.level.getInput().isPressed(0, 0, 20, 20)) {
            this.level.getData().skill(this.level);
        }
        */

        this.x += this.dx;
        this.y += this.dy;

        if (this.jump) {
            this.dy += SPEED_Y_INCREMENT;
        }

        if (this.x < 0) {
            this.x = 0;
            this.dx = 0;
        } else if (this.x + this.width > Resources.SCREEN_WIDTH) {
            this.x = Resources.SCREEN_WIDTH - this.width;
            this.dx = 0;
        }

        if (this.y < 0) {
            this.y = 0;
            this.dy = 0;
        } else if (this.y + this.height > Resources.SCREEN_HEIGHT) {
            this.y = Resources.SCREEN_HEIGHT - this.height;
            this.dy = 0;

            this.jump = false;
        }

        if (this.dx != 0) {
            ((AnimatedSprite) this.sprite).play(this.dx > 0 ? 2 : 1);
        } else {
            ((AnimatedSprite) this.sprite).stop();
        }

        ((AnimatedSprite) this.sprite).tick();
    }

}

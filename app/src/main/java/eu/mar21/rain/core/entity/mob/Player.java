package eu.mar21.rain.core.entity.mob;

import eu.mar21.rain.core.device.input.TouchZone;
import eu.mar21.rain.core.graphics.Notification;
import eu.mar21.rain.core.graphics.sprite.AnimatedSprite;
import eu.mar21.rain.core.level.Level;
import eu.mar21.rain.core.level.data.Skill;
import eu.mar21.rain.core.level.data.Statistics;
import eu.mar21.rain.core.utils.Direction;
import eu.mar21.rain.core.utils.Resources;

public class Player extends Mob {

    // Default params
    private static final double LIMIT_DX = 20.0;
    private static final double LIMIT_DY = -10.0;
    private static final double LIMIT_DX_STEP = 1.6;
    private static final double LIMIT_DY_STEP = 0.4;
    private static final int ANIM_DELTA = 8;
    private static final int[] ANIM_GRP0 = { 0 };
    private static final int[] ANIM_GRP1 = { 1, 2, 3, 4 };
    private static final int[] ANIM_GRP2 = { 5, 6, 7, 8 };

    // Params
    private final double speed;
    private boolean airborn;

    // Constructor
    public Player(Level level, double x, double y) {
        super(level, x, y, Resources.PLAYER[0].getWidth(), Resources.PLAYER[0].getHeight(), new AnimatedSprite(Resources.PLAYER, ANIM_DELTA, ANIM_GRP0, ANIM_GRP1, ANIM_GRP2), 0.0, 0.0);

        this.speed = LIMIT_DX_STEP * Resources.RES_MULTX;
        this.airborn = true;
    }

    // Methods
    @Override
    public void tick() {
        Direction direction = this.level.getInput().getDirection(TouchZone.ROW_4);

        if (direction == Direction.RIGHT) {
            this.dx = Math.min(this.dx + this.speed, LIMIT_DX * Resources.RES_MULTX);
        }

        if (direction == Direction.LEFT) {
            this.dx = Math.max(this.dx - this.speed, -LIMIT_DX * Resources.RES_MULTX);
        }

        if (direction == Direction.NONE) {
            if (this.dx > 0) {
                this.dx = Math.max(this.dx - this.speed, 0);
            } else if (this.dx < 0) {
                this.dx = Math.min(this.dx + this.speed, 0);
            }
        }

        if (this.level.getInput().isPressed(TouchZone.ROW_3)) {
            if (!this.airborn) {
                this.airborn = true;
                this.dy = LIMIT_DY * Resources.RES_MULTY;

                Statistics.STAT_TOTAL_JUMPS.add(1);
            }
        }

        if (this.level.getInput().isPressed(TouchZone.QUAD_LU)) {
            this.level.getData().useSkill(this);
        }

        if (this.level.getInput().isPressed(TouchZone.QUAD_RU)) {
            if (this.level.getData().selectNextSkill()) {
                this.level.showNotification(new Notification(Notification.NotificationStyle.PLAIN,"SKILL SELECTED", Skill.values()[this.level.getData().getSelectedSkill()].name()));
            }
        }

        this.x += this.dx;
        this.y += this.dy;

        if (this.airborn) {
            this.dy += LIMIT_DY_STEP * Resources.RES_MULTY;
        }

        if (this.x < 0) {
            this.x = 0;
            this.dx = 0;
        } else if (this.x + this.sx > Resources.SCREEN_WIDTH) {
            this.x = Resources.SCREEN_WIDTH - this.sx;
            this.dx = 0;
        }

        if (this.y < 0) {
            this.y = 0;
            this.dy = 0;
        } else if (this.y + this.sy > Resources.SCREEN_HEIGHT) {
            this.y = Resources.SCREEN_HEIGHT - this.sy;
            this.dy = 0;

            this.airborn = false;
        }

        if (this.dx != 0) {
            ((AnimatedSprite) this.sprite).play(direction == Direction.NONE ? (this.dx > 0 ? 2 : 1) : (direction == Direction.RIGHT ? 2 : 1));
        } else {
            ((AnimatedSprite) this.sprite).stop();
        }

        ((AnimatedSprite) this.sprite).tick();
    }

}

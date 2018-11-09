package eu.mar21.rain.core.entity.mob;

import eu.mar21.rain.core.graphics.Notification;
import eu.mar21.rain.core.graphics.sprite.AnimatedSprite;
import eu.mar21.rain.core.level.Level;
import eu.mar21.rain.core.level.data.Skill;
import eu.mar21.rain.core.utils.Input;
import eu.mar21.rain.core.utils.Resources;

public class Player extends Mob {

    public static final double SPEED_X_LIMIT = 20;
    public static final double SPEED_Y_LIMIT = -10;
    public static final double SPEED_X_INCREMENT = 1.6;
    public static final double SPEED_Y_INCREMENT = 0.4;

    public static final int ANIMATION_DELTA = 8;
    public static final int ANIMATION_FRAMEGROUP_0[] = { 0 };
    public static final int ANIMATION_FRAMEGROUP_1[] = { 1, 2, 3, 4 };
    public static final int ANIMATION_FRAMEGROUP_2[] = { 5, 6, 7, 8 };

    private final double speed;
    private boolean jump = true;

    public Player(double x, double y, Level level) {
        super(x, y, Resources.PLAYER[0].getWidth(), Resources.PLAYER[0].getHeight(), new AnimatedSprite(Resources.PLAYER, ANIMATION_DELTA, ANIMATION_FRAMEGROUP_0, ANIMATION_FRAMEGROUP_1, ANIMATION_FRAMEGROUP_2), 0, 0, level);

        this.speed = SPEED_X_INCREMENT;
    }

    @Override
    public void tick() {
        int move = 0;

        if (this.level.getInput().held(Input.ZONE.FOURTH_ROW_R) && !this.level.getInput().held(Input.ZONE.FOURTH_ROW_L)) {
            this.dx = Math.min(this.dx + this.speed, SPEED_X_LIMIT);
            move = 1;
        }

        if (this.level.getInput().held(Input.ZONE.FOURTH_ROW_L) && !this.level.getInput().held(Input.ZONE.FOURTH_ROW_R)) {
            this.dx = Math.max(this.dx - this.speed, -SPEED_X_LIMIT);
            move = -1;
        }

        if (!this.level.getInput().held(Input.ZONE.FOURTH_ROW_L) && !this.level.getInput().held(Input.ZONE.FOURTH_ROW_R)) {
            if (this.dx > 0) {
                this.dx = Math.max(this.dx - this.speed, 0);
            } else if (this.dx < 0) {
                this.dx = Math.min(this.dx + this.speed, 0);
            }
        }

        if (this.level.getInput().held(Input.ZONE.THIRD_ROW)) {
            if (!this.jump) {
                this.jump = true;
                this.dy = SPEED_Y_LIMIT;
            }
        }

        if (this.level.getInput().pressed(Input.ZONE.LEFT_UP_QUAD)) {
            this.level.getData().skill(this.level, this);
        }

        if (this.level.getInput().pressed(Input.ZONE.RIGHT_UP_QUAD)) {
            if (this.level.getData().selectedSkill() >= 3) {
                this.level.getData().setSkill(0);
            } else {
                this.level.getData().setSkill(this.level.getData().selectedSkill() + 1);
                this.level.showNotification(new Notification("SKILL SELECTED", Skill.values()[this.level.getData().selectedSkill() - 1].name(), null));
            }
        }

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
            ((AnimatedSprite) this.sprite).play(move == 0 ? (this.dx > 0 ? 2 : 1) : (move > 0 ? 2 : 1));
        } else {
            ((AnimatedSprite) this.sprite).stop();
        }

        ((AnimatedSprite) this.sprite).tick();
    }

}

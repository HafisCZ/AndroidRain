package eu.mar21.rain.core.level.data;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import eu.mar21.rain.core.entity.Entity;
import eu.mar21.rain.core.graphics.Notification;
import eu.mar21.rain.core.graphics.sprite.Sprite;
import eu.mar21.rain.core.level.Level;
import eu.mar21.rain.core.utils.DataStorage;
import eu.mar21.rain.core.utils.Resources;

public class PlayerData {

    private int health;
    private int shield;
    private int experience;
    private int energy;
    private int boost;
    private int boostDuration;
    private int score;

    private Skill skill;

    private int level;
    private int level_exp_pool;

    private static final double EXP_POOL = 40 * 60;
    private static final double EXP_POOL_MOD = 1.2;

    private static final Paint FONT_TEXT = new Paint();
    static {
        FONT_TEXT.setTextAlign(Paint.Align.CENTER);
        FONT_TEXT.setTextSize(20);
        FONT_TEXT.setTypeface(Typeface.MONOSPACE);
        FONT_TEXT.setColor(Color.YELLOW);
    }

    private int burnout = 0;

    private final Sprite icons[] = new Sprite[3];
    private final Sprite frames[] = new Sprite[2];
    private final Sprite bars[] = new Sprite[5];
    private final Sprite skills;

    private final int yoffset;
    private final int xoffset;

    private final Level glevel;

    public PlayerData(Level glevel) {
        this.glevel = glevel;

        this.health = DataStorage.INSTANCE.get("max_health", 5);
        this.shield = DataStorage.INSTANCE.get("max_shield", 5);

        this.energy = 0;
        this.experience = 0;
        this.skill = null;

        this.score = DataStorage.INSTANCE.get("score", 5);

        this.boost = 1;
        this.boostDuration = 0;

        this.level = DataStorage.INSTANCE.get("level", 1);
        this.level_exp_pool = (int) (EXP_POOL * Math.pow(EXP_POOL_MOD, this.level));

        frames[0] = new Sprite(Resources.BARS[0]);
        frames[1] = new Sprite(Resources.SKILLF);

        skills = new Sprite(Resources.SKILL, 1, 4);

        for (int i = 0; i < 3; i++) {
            icons[i] = new Sprite(Resources.ICONS[i]);
        }

        bars[0] = new Sprite(Resources.BARS[1], 1, 10);
        bars[1] = new Sprite(Resources.BARS[3], 1, 10);
        bars[2] = new Sprite(Resources.BARS[2], 1, 100);
        bars[3] = new Sprite(Resources.BARS[4], 1, 100);
        bars[4] = new Sprite(Resources.BARS[5], 1, 100);

        this.yoffset = Resources.BARS[0].getHeight() + 5;
        this.xoffset = Resources.ICONS[0].getWidth();
    }

    public void save() {
        DataStorage.INSTANCE.set("score", this.score);
        DataStorage.INSTANCE.set("level", this.level);
    }

    public void draw(Canvas c) {
        c.drawText("" + (this.score / 60), c.getWidth() / 2, 30, FONT_TEXT);

        for (int i = 0; i < (this.skill != null ? 3 : 2); i++) {
            icons[i].draw(c, xoffset - 10, 10 + yoffset * i);
            frames[0].draw(c, xoffset * 2, 10 + yoffset * i);
        }

        bars[0].setSpan(1, this.health);
        bars[0].draw(c, xoffset * 2, 10);

        bars[1].setSpan(1, this.shield);
        bars[1].draw(c, xoffset * 2, 10);

        bars[2].setSpan(1, (int) (100.0 * (double) this.experience / (double) this.level_exp_pool));
        bars[2].draw(c, xoffset * 2, 10 + yoffset);

        if (this.skill != null) {
            if (this.burnout > 0) {
                bars[4].setSpan(1, (int) (100.0 * (double) this.burnout / (double) this.skill.getDuration()));
                bars[4].draw(c, xoffset * 2, 10 + yoffset * 2);
            } else {
                bars[3].setSpan(1, this.energy);
                bars[3].draw(c, xoffset * 2, 10 + yoffset * 2);
            }

            frames[1].draw(c, xoffset * 2, 10 + yoffset * 3);
            skills.selectTile(0, this.skill.ordinal());
            skills.draw(c, xoffset * 2 + Resources.SKILLF.getWidth() / 2 - Resources.SKILL.getWidth() / 8, 10 + yoffset * 3 + Resources.SKILLF.getWidth() / 2 - Resources.SKILL.getWidth() / 8);
        }
    }

    public void damage() {
        if (this.health > 0) {
            if (this.shield > 0) {
                this.shield--;
            } else {
                this.health--;
            }
        }
    }

    public void setSkill(int ord) {
        if (this.burnout <= 0) {
            if (ord == 0) {
                this.skill = null;
            } else {
                this.skill = Skill.values()[ord - 1];
            }

            this.energy = 0;
        }
    }

    public void addExperienceBoost(int boost, int duration) {
        this.boost = boost;
        this.boostDuration = duration;

        this.score += 100 * 60;
    }

    public void tick() {
        this.score += 5;

        if (this.boostDuration > 0) {
            this.boostDuration--;
            this.experience += this.boost;
        } else {
            this.experience++;
        }

        if (this.experience >= this.level_exp_pool) {
            this.experience = 0;

            this.level++;
            this.level_exp_pool = (int) (EXP_POOL * Math.pow(EXP_POOL_MOD, this.level));

            this.glevel.showNotification(new Notification("LEVEL UP!", "YOU REACHED LEVEL " + this.level, null));
        }

        if (this.burnout > 0) {
            this.burnout--;
        }
    }

    public int getHealth() {
        return this.health;
    }

    public int getShield() {
        return this.shield;
    }

    public int getExperience() {
        return this.experience;
    }

    public int getEnergy() {
        return this.energy;
    }

    public int selectedSkill() {
        return this.skill == null ? 0 : this.skill.ordinal() + 1;
    }

    public void skill(Level level, Entity caster) {
        if (this.skill != null && this.burnout <= 0 && this.energy >= 100) {
            this.skill.applyEffect(caster.getCenterX(), caster.getCenterY(), level);
            this.burnout = this.skill.getDuration();
            this.energy = 0;
        }
    }

    public void addEnergy(int amount) {
        if (this.burnout <= 0 && this.skill != null) {
            this.energy += amount * this.skill.getRateMultiplier();
            if (this.energy >= 100) {
                this.energy = 100;
            }
        }

        this.score += 25 * 60;
    }

    public void addShield() {
        if (this.shield < this.health) {
            this.shield++;
        }

        this.score += 100 * 60;
    }

    public void addExperience(int amount) {
        this.experience += amount;
    }

}

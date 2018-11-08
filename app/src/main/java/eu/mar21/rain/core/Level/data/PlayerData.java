package eu.mar21.rain.core.level.data;

import android.graphics.Canvas;

import eu.mar21.rain.core.entity.Entity;
import eu.mar21.rain.core.graphics.sprite.Sprite;
import eu.mar21.rain.core.level.Level;
import eu.mar21.rain.core.utils.Resources;

public class PlayerData {

    private int health;
    private int shield;
    private int experience;
    private int energy;
    private int skill;

    private boolean consume = false;

    private final Sprite icons[] = new Sprite[3];
    private final Sprite frames[] = new Sprite[2];
    private final Sprite bars[] = new Sprite[5];
    private final Sprite skills;

    private final int yoffset;
    private final int xoffset;

    public PlayerData() {
        this.health = 5;
        this.shield = 5;
        this.energy = 0;
        this.experience = 0;
        this.skill = 1;

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

    public void draw(Canvas c) {
        for (int i = 0; i < 3; i++) {
            icons[i].draw(c, xoffset - 10, 10 + yoffset * i);
        }

        for (int i = 0; i < 3; i++) {
            frames[0].draw(c, xoffset * 2, 10 + yoffset * i);
        }

        bars[0].setSpan(1, this.health);
        bars[0].draw(c, xoffset * 2, 10);

        bars[1].setSpan(1, this.shield);
        bars[1].draw(c, xoffset * 2, 10);

        bars[2].setSpan(1, this.experience / 100);
        bars[2].draw(c, xoffset * 2, 10 + yoffset);

        if (this.consume || this.energy >= 100) {
            bars[4].setSpan(1, this.energy);
            bars[4].draw(c, xoffset * 2, 10 + yoffset * 2);
        } else {
            bars[3].setSpan(1, this.energy);
            bars[3].draw(c, xoffset * 2, 10 + yoffset * 2);
        }

        if (this.skill > 0) {
            frames[1].draw(c, xoffset * 2, 10 + yoffset * 3);
            skills.selectTile(0, this.skill - 1);
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

    public void tick() {
        this.experience++;
        if (this.experience > 10000) {
            this.experience = 0;
        }

        if (this.consume) {
            if (--this.energy < 1) {
                this.consume = false;
            }
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

    public void skill(Level level, Entity caster) {
        if (this.skill > 0 && !this.consume && this.energy >= 100) {
            Skill.SHOCKWAVE.applyEffect(caster.getCenterX(), caster.getCenterY(), level);
            this.consume = true;
        }
    }

    public void addEnergy(int amount) {
        if (!this.consume) {
            this.energy += amount * 5;
            if (this.energy >= 100) {
                this.energy = 100;
            }
        }
    }

    public void addShield() {
        if (this.shield < this.health) {
            this.shield++;
        }
    }

    public void addExperience(int amount) {
        this.experience += amount;
        if (this.experience > 10000) {
            this.experience = 0;
        }
    }

}

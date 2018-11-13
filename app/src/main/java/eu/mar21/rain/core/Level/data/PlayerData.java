package eu.mar21.rain.core.level.data;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;

import java.util.ArrayList;
import java.util.List;

import eu.mar21.rain.core.entity.Entity;
import eu.mar21.rain.core.graphics.Notification;
import eu.mar21.rain.core.graphics.sprite.Sprite;
import eu.mar21.rain.core.level.Level;
import eu.mar21.rain.core.utils.Resources;

public class PlayerData {

    private static final double EXP_POOL = 40;
    private static final double EXP_POOL_MOD = 1.2;

    private static final Paint FONT_TEXT = new Paint();
    static {
        FONT_TEXT.setTextAlign(Paint.Align.CENTER);
        FONT_TEXT.setTextSize(20);
        FONT_TEXT.setTypeface(Typeface.MONOSPACE);
        FONT_TEXT.setColor(Color.YELLOW);
    }

    private final Level level;

    private int playerHealth;
    private int playerShield;

    private int playerEnergy;
    private int playerEnergyBurnout;
    private int playerEnergyRate;

    private int playerExp;
    private int playerNextLevelExp;

    private int playerExpBoostDuration;
    private int playerExpBoost;

    private Skill selectedSkill;
    private List<Skill> availableSkills;

    private Sprite iconFrame;
    private Sprite iconSkills;
    private Sprite iconSmall[];

    private Sprite barFrame;
    private Sprite barBars[];

    private int xoff;
    private int yoff;

    private int counter = 0;

    public PlayerData(Level level) {
        this.level = level;

        this.playerHealth = Statistics.PLAYER_MAX_HEALTH.get();
        this.playerShield = Statistics.PLAYER_DEF_SHIELD.get();

        this.playerEnergy = 0;
        this.playerEnergyBurnout = 0;
        this.playerEnergyRate = Statistics.PLAYER_UPGRADE_BETTER_NODES.get() != 0 ? 2 : 1;

        this.playerExp = 0;
        this.playerNextLevelExp = (int) (EXP_POOL * Math.pow(EXP_POOL_MOD, Statistics.PLAYER_LEVEL.get()));

        this.playerExpBoostDuration = 0;
        this.playerExpBoost = 0;

        this.selectedSkill = null;
        this.availableSkills = new ArrayList<>();

        if (Statistics.PLAYER_SKILL_SHOCKWAVE.get() != 0) {
            this.availableSkills.add(Skill.SHOCKWAVE);
        }

        if (Statistics.PLAYER_SKILL_DOUBLE_EXP.get() != 0) {
            this.availableSkills.add(Skill.EXPERIENCE_SPAWN);
        }

        if (Statistics.PLAYER_SKILL_SHIELD.get() != 0) {
            this.availableSkills.add(Skill.SHIELD_SPAWN);
        }

        this.iconFrame = new Sprite(Resources.SKILLF);
        this.iconSkills = new Sprite(Resources.SKILL, 1, 4);
        this.iconSmall = new Sprite[] {
            new Sprite(Resources.ICONS[0]),
            new Sprite(Resources.ICONS[1]),
            new Sprite(Resources.ICONS[2])
        };

        this.barFrame = new Sprite(Resources.BARS[0]);
        this.barBars = new Sprite[] {
                new Sprite(Resources.BARS[1], 1, 10),
                new Sprite(Resources.BARS[3], 1, 10),
                new Sprite(Resources.BARS[2], 1, 100),
                new Sprite(Resources.BARS[4], 1, 100),
                new Sprite(Resources.BARS[5], 1, 100)
        };

        this.yoff = Resources.BARS[0].getHeight() + 5;
        this.xoff = Resources.ICONS[0].getWidth();
    }

    public void save() {
        Statistics.STAT_COUNT_EXP.add(this.playerExp);
        Statistics.save();
    }

    public void draw(Canvas c) {
        c.drawText("" + Statistics.PLAYER_SCORE.get(), c.getWidth() / 2, 30, FONT_TEXT);

        for (int i = 0; i < (this.selectedSkill == null ? 2 : 3); i++) {
            this.iconSmall[i].draw(c, yoff - 10, yoff * i + 10);
            this.barFrame.draw(c, xoff * 2, yoff * i + 10);
        }

        this.barBars[0].setSpan(1, this.playerHealth);
        this.barBars[1].setSpan(1, this.playerShield);
        this.barBars[2].setSpan(1, (int) (100.0 * (double) this.playerExp / (double) this.playerNextLevelExp));

        this.barBars[0].draw(c, xoff * 2, 10);
        this.barBars[1].draw(c, xoff * 2, 10);

        this.barBars[2].draw(c, xoff * 2, yoff + 10);
        if (this.playerExpBoostDuration > 0) {
            c.drawText(this.playerExpBoost  + "X", xoff * 3 + Resources.BARS[0].getWidth(), yoff + 30, FONT_TEXT);
        }

        if (this.selectedSkill != null) {
            if (this.playerEnergyBurnout > 0) {
                this.barBars[4].setSpan(1, Math.min(100, (int) (100.0 * (double) this.playerEnergyBurnout / (double) (this.selectedSkill.getDuration() * 60))));
                this.barBars[4].draw(c, xoff * 2, yoff * 2 + 10);
            } else {
                this.barBars[3].setSpan(1, Math.min(100, (int) (100.0 * (double) this.playerEnergy / (double) this.selectedSkill.getPowerRequired())));
                this.barBars[3].draw(c, xoff * 2, yoff * 2 + 10);
            }

            this.iconFrame.draw(c, xoff * 2, yoff * 3 + 10);

            this.iconSkills.selectTile(0, this.selectedSkill.ordinal());
            this.iconSkills.draw(c, xoff * 2 + Resources.SKILLF.getWidth() / 2 - Resources.SKILL.getWidth() / 8, 10 + yoff * 3 + Resources.SKILLF.getWidth() / 2 - Resources.SKILL.getWidth() / 8);
        }
    }

    public void damage() {
        if (this.playerHealth > 0) {
            if (this.playerShield > 0) {
                this.playerShield--;
            } else {
                this.playerHealth--;
            }
        }

        Statistics.STAT_COUNT_DAMAGE.add();
    }

    public int getPlayerHealth() {
        return this.playerHealth;
    }

    public void addExperienceBoost(int multiplier, int duration) {
        this.playerExpBoost = multiplier;
        this.playerExpBoostDuration = duration;

        Statistics.PLAYER_SCORE.add(200);
    }

    public int getSelectedSkill() {
        return this.selectedSkill == null ? -1 : this.selectedSkill.ordinal();
    }

    public int getRequiredExperience() {
        return this.playerNextLevelExp;
    }

    public Skill getSkill() {
        return this.selectedSkill;
    }

    public void useSkill(Entity caster) {
        if (this.selectedSkill != null && this.playerEnergyBurnout <= 0 && this.playerEnergy >= this.selectedSkill.getPowerRequired()) {
            this.selectedSkill.applyEffect(caster.getCenterX(), caster.getCenterY(), this.level);
            this.playerEnergyBurnout = this.selectedSkill.getDuration() * 60;
            this.playerEnergy = 0;

            Statistics.STAT_COUNT_ACTIVATE.add();
        }
    }

    public boolean selectNextSkill() {
        if (this.availableSkills.size() > 0 && this.playerEnergyBurnout <= 0) {
            if (this.selectedSkill == null) {
                this.selectedSkill = this.availableSkills.get(0);
            } else {
                this.selectedSkill = this.availableSkills.get((this.availableSkills.indexOf(this.selectedSkill) + 1) % this.availableSkills.size());
            }

            return true;
        } else {
            return false;
        }
    }

    public void addEnergy() {
        addEnergy(1);
    }

    public void addEnergy(int externalMult) {
        if (this.playerEnergyBurnout <= 0 && this.selectedSkill != null) {
            this.playerEnergy += this.playerEnergyRate * externalMult;

            if (this.playerEnergy >= this.selectedSkill.getPowerRequired()) {
                this.playerEnergy = this.selectedSkill.getPowerRequired();
            }
        }

        Statistics.PLAYER_SCORE.add(25);
    }

    public void addShield() {
        if (this.playerShield < this.playerHealth) {
            this.playerShield++;
        }

        Statistics.PLAYER_SCORE.add(100);
    }

    public void addExperience(int amount) {
        this.playerExp += amount;
    }

    public void tick() {
        if (counter++ > 60) {
            counter = 0;

            Statistics.PLAYER_SCORE.add(2);

            if (this.playerExpBoostDuration > 0) {
                this.playerExpBoostDuration--;
                this.playerExp += this.playerExpBoost;
            } else {
                this.playerExp++;
            }

            if (this.playerExp >= this.playerNextLevelExp) {
                this.playerExp = 0;

                Statistics.PLAYER_LEVEL.add();
                Statistics.PLAYER_SPENDABLE_POINTS.add();

                this.playerNextLevelExp = (int) (EXP_POOL * Math.pow(EXP_POOL_MOD, Statistics.PLAYER_LEVEL.get()));

                this.level.showNotification(new Notification(Notification.NotificationStyle.YELLOW,"LEVEL UP!", "YOU REACHED LEVEL " + Statistics.PLAYER_LEVEL.get()));
            }
        }

        if (this.playerEnergyBurnout > 0) {
            this.playerEnergyBurnout--;
        }
    }

    public void addPoint() {
        Statistics.PLAYER_SPENDABLE_POINTS.add();
    }

}

package eu.mar21.rain.core.level.data;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import eu.mar21.rain.core.graphics.Drawable;
import eu.mar21.rain.core.graphics.Notification;
import eu.mar21.rain.core.graphics.sprite.Sprite;
import eu.mar21.rain.core.level.Level;
import eu.mar21.rain.core.utils.Resources;
import eu.mar21.rain.core.utils.Tuple;

public class LevelData implements Drawable {

    // Default params
    private static final Random RANDOM = new Random();

    private static final int EXP_POOL = 60;
    private static final float EXP_POOL_EXPONENT = 1.1f;

    // Params
    private final Queue<Notification> notifications = new LinkedList<>();
    private final Queue<Tuple<Integer, Integer>> boosts = new LinkedList<>();
    private final Level level;

    private int health;
    private int shield;
    private int energy;
    private int energyDecay;
    private int energyMultiplier;
    private int experience;
    private int experienceNeeded;
    private int counter;
    private long time;

    private int skillIndex;
    private Skill skill;
    private List<Skill> skills = new ArrayList<>();

    private Sprite iconFrame;
    private Sprite iconSkills;
    private Sprite[] iconSmall;

    private Sprite barFrame;
    private Sprite[] barBars;

    private int xoff;
    private int yoff;

    // Constructor
    public LevelData(Level level) {
        this.level = level;

        this.health = Data.PLAYER_HEALTH.get();
        this.shield = 0;
        this.energy = 0;
        this.energyDecay = 0;
        this.energyMultiplier = 1 + Data.UPGRADE_ENERGY_MULT.get();
        this.experience = 0;
        this.experienceNeeded = (int) (EXP_POOL * Math.pow(EXP_POOL_EXPONENT, Data.PLAYER_LEVEL.get()));
        this.time = 0;
        this.counter = 0;

        this.skillIndex = 0;
        this.skill = null;

        if (Data.UPGRADE_SKILL_SHOCK.get() > 0) this.skills.add(Skill.SHOCKWAVE);
        if (Data.UPGRADE_SKILL_XPBOOST.get() > 0) this.skills.add(Skill.EXPERIENCE_SPAWN);
        if (Data.UPGRADE_SKILL_SHIELD.get() > 0) this.skills.add(Skill.SHIELD_SPAWN);

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

    // Methods
    public void save() {
        if (Data.STAT_LONGEST_GAME.get() < this.time) {
            Data.STAT_LONGEST_GAME.set((int) this.time);
        }

        Award.save();
        Data.save();
    }

    public void applyLightning() {
        Data.STAT_LIGHTNING_HIT.add(1);

        applyEnergy(10 + RANDOM.nextInt(20));
    }

    public void applyDamage() {
        Data.STAT_DMG_TAKEN.add(1);

        if (this.shield > 0) {
            this.shield--;
        } else if (this.health > 0) {
            this.health--;
        }
    }

    public void applyRandom() {
        Data.STAT_RANDOM_COLLECTED.add(1);

        int rnd = RANDOM.nextInt(Data.UPGRADE_RANDOM.get());
        if (rnd == 1) {
            int exp = 5 + RANDOM.nextInt(this.experienceNeeded / 5);
            applyExperience(exp);

            showNotification(Notification.NotificationStyle.GREEN,"ITEM RECEIVED", experience + " EXP");
        } else if (rnd == 2) {
            applyShield();

            showNotification(Notification.NotificationStyle.GREEN,"ITEM RECEIVED", "SHIELD");
        } else if (rnd == 3) {
            int mul = 2 + RANDOM.nextInt(Data.PLAYER_LEVEL.get());
            int mdr = 10 + RANDOM.nextInt(Data.PLAYER_LEVEL.get());
            applyExperienceMultiplier(mul, mdr);

            showNotification(Notification.NotificationStyle.GREEN, "ITEM RECEIVED", mul + "X EXP FOR " + mdr + " SECONDS");
        } else {
            if (rnd == 4 && RANDOM.nextInt(20) == 0) {
                Data.PLAYER_POINTS.add(1);
                showNotification(Notification.NotificationStyle.YELLOW, "ITEM RECEIVED", "LEVEL POINT");

                return;
            }

            if (this.skill != null && this.energyDecay <= 0) {
                int eng = 1 + RANDOM.nextInt(this.skill.getPowerRequired() / 4);
                applyEnergy(eng);

                showNotification(Notification.NotificationStyle.GREEN, "ITEM RECEIVED", eng + " ENERGY");
            }
        }
    }

    public void applyJump() {
        Data.STAT_TOTAL_JUMPS.add(1);
    }

    public void applyExperienceMultiplier(int multiplier, int duration) {
        Data.PLAYER_SCORE.add(200);

        this.boosts.add(new Tuple<>(duration, multiplier));
    }

    private void tryAward(Award award) {
        Award a = award.tryAward();
        if (a != null) {
            showNotification(Notification.NotificationStyle.YELLOW,  a.getLabel(), "Achievement received!");
        }
    }

    private void tryAward(Award award, boolean val) {
        Award a = award.tryAward(val);
        if (a != null) {
            showNotification(Notification.NotificationStyle.YELLOW,  a.getLabel(), "Achievement received!");
        }
    }

    @Override
    public void draw(Canvas c) {
        c.drawText("" + Data.PLAYER_SCORE.get(), (float) (c.getWidth() / 2.0), 30.0f, Resources.PAINT_M_Y_20_C);

        for (int i = 0; i < (this.skill == null ? 2 : 3); i++) {
            this.iconSmall[i].draw(c, yoff - 10, yoff * i + 10);
            this.barFrame.draw(c, xoff * 2, yoff * i + 10);
        }

        this.barBars[0].setSpan(1, this.health);
        this.barBars[1].setSpan(1, this.shield);
        this.barBars[2].setSpan(1, Math.min(100, (int) (100.0 * (double) this.experience / (double) this.experienceNeeded)));

        this.barBars[0].draw(c, xoff * 2, 10);
        this.barBars[1].draw(c, xoff * 2, 10);

        this.barBars[2].draw(c, xoff * 2, yoff + 10);
        if (this.boosts.size() > 0) {
            c.drawText(this.boosts.peek().second  + "X", xoff * 3 + Resources.BARS[0].getWidth(), yoff + 30, Resources.PAINT_M_Y_20_C);
        }

        if (this.skill != null) {
            if (this.energyDecay > 0) {
                this.barBars[4].setSpan(1, Math.min(100, (int) (100.0 * (double) this.energyDecay / (double) (this.skill.getDuration() * 60))));
                this.barBars[4].draw(c, xoff * 2, yoff * 2 + 10);
            } else {
                this.barBars[3].setSpan(1, Math.min(100, (int) (100.0 * (double) this.energy / (double) this.skill.getPowerRequired())));
                this.barBars[3].draw(c, xoff * 2, yoff * 2 + 10);
            }

            this.iconFrame.draw(c, xoff * 2, yoff * 3 + 10);

            this.iconSkills.selectTile(0, this.skill.ordinal());
            this.iconSkills.draw(c, xoff * 2 + Resources.SKILLF.getWidth() / 2.0 - Resources.SKILL.getWidth() / 8.0, 10 + yoff * 3 + Resources.SKILLF.getWidth() / 2 - Resources.SKILL.getWidth() / 8.0);
        }

        if (!this.notifications.isEmpty()) {
            this.notifications.peek().draw(c);
        }
    }

    private void showNotification(Notification.NotificationStyle s, String label, String description) {
        this.notifications.add(new Notification(s, label, description));
    }

    public void applySkill() {
        if (this.skill != null && this.energyDecay <= 0 && this.energy >= this.skill.getPowerRequired()) {
            Data.STAT_SKILL_ACTIVATIONS.add(1);

            this.skill.applyEffect(this.level.getPlayer().getCX(), this.level.getPlayer().getCY(), this.level);
            this.energyDecay = this.skill.getDuration() * 60;
            this.energy = 0;
        }
    }

    public void selectSkill() {
        if (this.energyDecay <= 0) {
            if (++this.skillIndex > this.skills.size()) {
                this.skillIndex = 0;
                this.skill = null;
            } else {
                this.skill = this.skills.get(this.skillIndex - 1);

                showNotification(Notification.NotificationStyle.PLAIN,"SKILL SELECTED", this.skill.name());
            }
        }
    }

    public void applyEnergy(int amount) {
        Data.PLAYER_SCORE.add(25);
        Data.STAT_ENERGY_COLLECTED.add(1);

        if (this.energyDecay <= 0 && this.skill != null) {
            this.energy += this.energyMultiplier * amount;
            if (this.energy >= this.skill.getPowerRequired()) {
                this.energy = this.skill.getPowerRequired();
            }
        }
    }

    public void applyShield() {
        Data.PLAYER_SCORE.add(100);
        Data.STAT_SHIELDS_COLLECTED.add(1);

        if (this.shield < this.health) {
            this.shield++;
        }
    }

    private void applyExperience(int amount) {
        Data.STAT_TOTAL_EXP.add(amount);

        this.experience += amount;
    }

    public boolean isDead() {
        return this.health <= 0;
    }

    public void tick() {
        if (++this.counter > 60) {
            this.counter = 0;
            this.time++;

            Data.PLAYER_SCORE.add(2);

            Tuple<Integer, Integer> boost = this.boosts.peek();
            if (boost != null) {
                if (--boost.first < 1) {
                    this.boosts.remove();
                }

                applyExperience(boost.second);
            } else {
                applyExperience(1);
            }

            if (this.experience >= this.experienceNeeded) {
                this.experience = 0;

                Data.PLAYER_LEVEL.add(1);
                Data.PLAYER_POINTS.add(1);

                this.experienceNeeded = (int) (EXP_POOL * Math.pow(EXP_POOL_EXPONENT, Data.PLAYER_LEVEL.get()));

                showNotification(Notification.NotificationStyle.YELLOW,"LEVEL UP!", "YOU REACHED LEVEL " + Data.PLAYER_LEVEL.get());
            }

            tryAward(Award.HEALTH);
            tryAward(Award.DAMAGE_1K);
            tryAward(Award.JUMP_666);
            tryAward(Award.LEVEL_1);
            tryAward(Award.NODE_1K);
            tryAward(Award.SHIELD_1K);
            tryAward(Award.STAR_1K);
            tryAward(Award.STRIKE_1);
            tryAward(Award.SCORE_100K);
            tryAward(Award.SHIELD_FULL, this.shield >= 10);
        }

        if (this.energyDecay > 0) {
            this.energyDecay--;
        }

        Notification n = this.notifications.peek();
        if (n != null) {
            n.update();

            if (n.isRemoved()) {
                this.notifications.remove();
            }
        }
    }

}

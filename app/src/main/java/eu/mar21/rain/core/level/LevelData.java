package eu.mar21.rain.core.level;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import eu.mar21.rain.core.graphics.Announcement;
import eu.mar21.rain.core.graphics.Drawable;
import eu.mar21.rain.core.graphics.Notification;
import eu.mar21.rain.core.graphics.sprite.Sprite;
import eu.mar21.rain.core.level.data.Award;
import eu.mar21.rain.core.level.data.Data;
import eu.mar21.rain.core.level.data.Skill;
import eu.mar21.rain.core.level.event.Event;
import eu.mar21.rain.core.level.event.StormEvent;
import eu.mar21.rain.core.utils.Number;
import eu.mar21.rain.core.utils.Resources;
import eu.mar21.rain.core.utils.Timer;
import eu.mar21.rain.core.utils.functional.Tuple;

public class LevelData implements Drawable {

    // Default params
    private static final Random RANDOM = new Random();

    private static final int EXP_POOL = 60;
    private static final float EXP_POOL_EXPONENT = 1.1f;

    // Params
    private final Queue<Notification> notifications = new LinkedList<>();
    private final Queue<Announcement> announcements = new LinkedList<>();
    private final Queue<Tuple<Integer, Integer>> boosts = new LinkedList<>();
    private final List<Tuple<Integer, Integer>> battery = new ArrayList<>();
    private final Level level;

    private int health;
    private int armor;
    private int experience;
    private int experienceAdv;

    private int eventChance;
    private Event event;

    private int skillIndex;
    private Skill skill;
    private List<Skill> skills = new ArrayList<>();

    private Sprite iconFrame;
    private Sprite iconSkills;
    private Sprite barFrame;
    private Sprite[] iconSmall;
    private Sprite[] iconBattery;
    private Sprite[] barBars;

    private int xoff;
    private int yoff;

    private long time;
    private Timer updateTimer;
    private Timer eventTimer;

    // Constructor
    public LevelData(Level level) {
        this.level = level;

        this.time = 0;

        this.health = Data.PLAYER_HEALTH.get();
        this.armor = 0;
        this.experience = 0;
        this.experienceAdv = (int) (EXP_POOL * Math.pow(EXP_POOL_EXPONENT, Data.PLAYER_LEVEL.get()));

        for (int i = 0; i < Data.UPGRADE_BATTERY.get(); i++) {
            this.battery.add(new Tuple<>(0, 0));
        }

        this.eventChance = 1;
        this.event = null;

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

        this.iconBattery = new Sprite[Resources.BATTERY.length];
        for (int i = 0; i < Resources.BATTERY.length; i++) {
            this.iconBattery[i] = new Sprite(Resources.BATTERY[i]);
        }

        this.barFrame = new Sprite(Resources.BARS[0]);
        this.barBars = new Sprite[] {
                new Sprite(Resources.BARS[1], 1, 10),
                new Sprite(Resources.BARS[3], 1, 10),
                new Sprite(Resources.BARS[2], 1, 100)
        };

        this.yoff = Resources.BARS[0].getHeight() + 5;
        this.xoff = Resources.ICONS[0].getWidth();

        this.updateTimer = new Timer(() -> {
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

            if (this.experience >= this.experienceAdv) {
                this.experience = 0;

                Data.PLAYER_LEVEL.add(1);
                Data.PLAYER_POINTS.add(1);

                this.experienceAdv = (int) (EXP_POOL * Math.pow(EXP_POOL_EXPONENT, Data.PLAYER_LEVEL.get()));

                showNotification(Notification.NotificationStyle.YELLOW,"LEVEL UP!", "You reached level " + Data.PLAYER_LEVEL.get());
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
            tryAward(Award.SHIELD_FULL);
            tryAward(Award.EVENT_1);
        }, 60);

        this.eventTimer = new Timer(() -> {
            if (Number.between(0, 10) < this.eventChance) {
                this.eventChance = 1;

                int rnd = RANDOM.nextInt(1);
                if (rnd == 0) {
                    startEvent(new StormEvent(this.level));
                    showAnnouncement("Thunderstorm", "Event started");
                }
            } else {
                this.eventChance++;
            }
        }, 3600);
    }

    // Methods
    public void save() {
        if (Data.STAT_LONGEST_GAME.get() < this.time) {
            Data.STAT_LONGEST_GAME.set((int) this.time);
        }

        Data.STAT_TIME_PLAYED.add((int) this.time);

        Award.save();
        Data.save();
    }

    private void startEvent(Event event) {
        if (this.event == null) {
            this.event = event;
            this.event.onStart();

            Data.STAT_EVENTS.add(1);
        }
    }

    public void applyLightning() {
        Data.STAT_LIGHTNING_HIT.add(1);

        applyEnergy(10 + RANDOM.nextInt(20));
        applyDamage();
    }

    public void applyDamage() {
        Data.STAT_DMG_TAKEN.add(1);

        if (this.armor > 0) {
            this.armor--;
        } else if (this.health > 0) {
            this.health--;
        }
    }

    public void applyRandom() {
        Data.STAT_RANDOM_COLLECTED.add(1);

        int rnd = RANDOM.nextInt(Data.UPGRADE_RANDOM.get());
        if (rnd == 1) {
            int exp = 5 + RANDOM.nextInt(this.experienceAdv / 5);
            applyExperience(exp);

            showNotification(Notification.NotificationStyle.GREEN,"ITEM RECEIVED", exp + " EXP");
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

            if (this.skill != null) {
                int eng = Number.between(1, 11);
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

    @Override
    public void draw(Canvas c) {
        c.drawText("" + Data.PLAYER_SCORE.get(), (float) (c.getWidth() / 2.0), 30.0f, Resources.PAINT_M_Y_20_C);

        for (int i = 0; i < 2; i++) {
            this.iconSmall[i].draw(c, yoff - 10, yoff * i + 10);
            this.barFrame.draw(c, xoff * 2, yoff * i + 10);
        }

        this.barBars[0].setSpan(1, this.health);
        this.barBars[1].setSpan(1, this.armor);
        this.barBars[2].setSpan(1, Math.min(100, (int) (100.0 * (double) this.experience / (double) this.experienceAdv)));

        this.barBars[0].draw(c, xoff * 2, 10);
        this.barBars[1].draw(c, xoff * 2, 10);

        this.barBars[2].draw(c, xoff * 2, yoff + 10);
        if (this.boosts.size() > 0) {
            c.drawText(this.boosts.peek().second  + "X", xoff * 3 + Resources.BARS[0].getWidth(), yoff + 30, Resources.PAINT_M_Y_20_C);
        }

        for (int i = 0; i < this.battery.size(); i++) {
            this.iconBattery[this.battery.get(i).second > 0 ? 11 : this.battery.get(i).first].draw(c, xoff * 5 + i * xoff * 1.8, yoff * 3 + 10);
        }

        if (this.skill != null) {
            this.iconFrame.draw(c, xoff * 2, yoff * 3 + 1);

            this.iconSkills.selectTile(0, this.skill.ordinal());
            this.iconSkills.draw(c, xoff * 2 + Resources.SKILLF.getWidth() / 2.0 - Resources.SKILL.getWidth() / 8.0, 1 + yoff * 3 + Resources.SKILLF.getWidth() / 2.0 - Resources.SKILL.getWidth() / 8.0);
        }

        if (!this.notifications.isEmpty()) {
            this.notifications.peek().draw(c);
        }

        Announcement a = this.announcements.peek();
        if (a != null) {
            a.draw(c);
            if (a.isRemoved()) {
                this.announcements.remove();
            }
        }
    }

    private void showNotification(Notification.NotificationStyle s, String label, String description) {
        this.notifications.add(new Notification(s, label, description));
    }

    private void showAnnouncement(String label, String description) {
        this.announcements.add(new Announcement(label, description));
    }

    public void applySkill() {
        if (this.skill != null) {
            List<Tuple<Integer, Integer>> bats = new ArrayList<>();

            for (int i = 0; i < this.battery.size(); i++) {
                if (this.battery.get(i).first == 10) {
                    bats.add(this.battery.get(i));
                }

                if (bats.size() >= this.skill.getPower()) {
                    break;
                }
            }

            if (bats.size() == this.skill.getPower()) {
                Data.STAT_SKILL_ACTIVATIONS.add(1);

                this.skill.applyEffect(this.level, this.level.getPlayer().getCX(), this.level.getPlayer().getCY());

                for (int i = 0; i < bats.size(); i++) {
                    bats.get(i).first = 0;
                    bats.get(i).second = (60 * this.skill.getDecay()) / (1 + Data.UPGRADE_BATTERY_DECAY_RATE.get());
                }
            }
        }
    }

    public void selectSkill() {
        if (++this.skillIndex > this.skills.size()) {
            this.skillIndex = 0;
            this.skill = null;
        } else {
            this.skill = this.skills.get(this.skillIndex - 1);

            showNotification(Notification.NotificationStyle.PLAIN,"SKILL SELECTED", this.skill.name());
        }
    }

    public void applyEnergy(int amount) {
        Data.PLAYER_SCORE.add(25);
        Data.STAT_ENERGY_COLLECTED.add(amount);

        int energy = amount;

        for (int i = 0; i < this.battery.size(); i++) {
            Tuple<Integer, Integer> bat = this.battery.get(i);

            if (bat.second <= 0) {
                int space = 10 - bat.first;

                if (energy > space) {
                    energy -= space;
                    bat.first = 10;
                } else {
                    bat.first += energy;
                    break;
                }
            }
        }
    }

    public void applyShield() {
        Data.PLAYER_SCORE.add(100);
        Data.STAT_SHIELDS_COLLECTED.add(1);

        if (this.armor < this.health) {
            this.armor++;
        }

        if (this.armor > Data.STAT_SHIELDS_MAX.get()) {
            Data.STAT_SHIELDS_MAX.set(this.armor);
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
        this.updateTimer.tick();
        this.eventTimer.tick();

        for (Tuple<Integer, Integer> bat : this.battery) {
            if (bat.second > 0) {
                bat.second--;
            }
        }

        Notification n = this.notifications.peek();
        if (n != null) {
            n.update();

            if (n.isRemoved()) {
                this.notifications.remove();
            }
        }

        if (this.event != null) {
            this.event.onUpdate();

            if (this.event.isRemoved()) {
                this.event.onExit();
                this.event = null;

                showAnnouncement("Event ended", null);
            }
        }
    }

}

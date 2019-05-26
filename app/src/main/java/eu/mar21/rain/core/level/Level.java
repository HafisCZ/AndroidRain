package eu.mar21.rain.core.level;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import eu.mar21.rain.core.device.input.InputListener;
import eu.mar21.rain.core.device.input.TouchZone;
import eu.mar21.rain.core.entity.Entity;
import eu.mar21.rain.core.entity.item.Item;
import eu.mar21.rain.core.entity.mob.Mob;
import eu.mar21.rain.core.entity.mob.Player;
import eu.mar21.rain.core.entity.particle.FlashParticle;
import eu.mar21.rain.core.entity.particle.Particle;
import eu.mar21.rain.core.entity.particle.RainParticle;
import eu.mar21.rain.core.entity.spawner.AcidSpawner;
import eu.mar21.rain.core.entity.spawner.ArmorSpawner;
import eu.mar21.rain.core.entity.spawner.EnergySpawner;
import eu.mar21.rain.core.entity.spawner.GenericSpawner;
import eu.mar21.rain.core.entity.spawner.RainSpawner;
import eu.mar21.rain.core.entity.spawner.Spawner;
import eu.mar21.rain.core.entity.spawner.StarSpawner;
import eu.mar21.rain.core.utils.Resources;

public class Level {

    // Entity lists
    private final List<Mob> mobs = new ArrayList<>();
    private final List<Item> items = new ArrayList<>();
    private final List<Spawner> spawners = new ArrayList<>();
    private final List<Particle> particles = new ArrayList<>();

    // Level data
    private LevelData data;
    private InputListener input;

    // Params
    private boolean exit;
    private boolean frozen;

    private Player player;
    private RainSpawner rain;

    // Constructor
    public Level(InputListener input) {
        this.input = input;
        this.exit = false;
        this.frozen = false;

        this.rain = new RainSpawner(this, 0.0, -20.0, Resources.SCREEN_WIDTH, 0.0, 1, 0, 5);
    }

    // Methods
    public void reset() {
        this.exit = false;

        this.mobs.clear();
        this.items.clear();
        this.spawners.clear();

        for (Iterator<Particle> iterator = this.particles.iterator(); iterator.hasNext();) {
            Particle p = iterator.next();

            if (p.isRemoved() || !(p instanceof RainParticle)) {
                iterator.remove();
            }
        }

        this.data = new LevelData(this);
        this.player = new Player(this, (Resources.SCREEN_WIDTH - Resources.PLAYER[0].getWidth()) / 2, Resources.SCREEN_HEIGHT);

        this.spawners.add(new GenericSpawner(this, 0, 0, Resources.SCREEN_WIDTH, 0, 20 * 60, 10 * 60, 1, (L, X, Y) -> add(new FlashParticle(this, X, 10))));

        this.spawners.add(new AcidSpawner(this,0, -50, Resources.SCREEN_WIDTH, 0,  20, 5, 2));
        this.spawners.add(new ArmorSpawner(this,0, -50, Resources.SCREEN_WIDTH, 0,  (60 * 60) >> 1, 10 * 60, 1));
        this.spawners.add(new EnergySpawner(this,0, -50, Resources.SCREEN_WIDTH, 0,  60, 60, 1));
        this.spawners.add(new StarSpawner(this,0, -50, Resources.SCREEN_WIDTH, 0,  20 * 60, 0, 1));
    }

    public void add(Spawner s) {
        this.spawners.add(s);
    }

    public void add(Mob m) {
        this.mobs.add(m);
    }

    public void add(Item i) {
        this.items.add(i);
    }

    public void add(Particle p) {
        this.particles.add(p);
    }

    public InputListener getInput() {
        return this.input;
    }

    public LevelData getData() {
        return this.data;
    }

    public void draw(Canvas c) {
        if (!this.exit) {
            for (int i = 0; i < 7; i++) {
                c.drawBitmap(Resources.BACKGROUND[i], (int) ((getPlayer().getCX() - Resources.SCREEN_WIDTH / 2) / (Resources.SCREEN_WIDTH / 8) * - Math.pow(1.55, i) - 100 / 2), 0, null);
            }
        } else {
            for (int i = 0; i < 7; i++) {
                c.drawBitmap(Resources.BACKGROUND[i], -50,0, null);
            }
        }

        for (Particle p : this.particles) {
            p.draw(c);
        }

        for (Mob m : this.mobs) {
            m.draw(c);
        }

        for (Item i : this.items) {
            i.draw(c);
        }

        if (this.player != null) {
            this.player.draw(c);
            this.data.draw(c);
        }
    }

    public boolean isFrozen() {
        return this.frozen;
    }

    public List<Mob> getMobs() {
        return this.mobs;
    }

    public Player getPlayer() {
        return this.player;
    }

    public boolean isCollidingPlayerAABB(Entity e) {
        return this.player != null && this.player.isCollidingAABB(e);
    }

    public void tick() {
        if (this.frozen && this.input.isPressed(TouchZone.HALF_DOWN)) {
            this.frozen = false;
        } else if (this.frozen && this.input.isPressed(TouchZone.HALP_UP)) {
            this.exit = true;
            this.frozen = false;
            this.data.save();
        } else if (this.input.isPressed(TouchZone.QUAD_CU)) {
            this.frozen = true;
            this.input.reset();
        }

        if (!frozen) {
            if (this.player != null) {
                this.player.tick();
            }

            this.rain.tick();

            for (Iterator<Spawner> iterator = this.spawners.iterator(); iterator.hasNext();) {
                Spawner spawner = iterator.next();
                spawner.tick();
                if (spawner.isRemoved()) {
                    iterator.remove();
                }
            }

            for (Iterator<Mob> iterator = this.mobs.iterator(); iterator.hasNext();) {
                Entity entity = iterator.next();
                entity.tick();
                if (entity.isRemoved()) {
                    iterator.remove();
                }
            }

            for (Iterator<Item> iterator = this.items.iterator(); iterator.hasNext();) {
                Entity entity = iterator.next();
                entity.tick();
                if (entity.isRemoved()) {
                    iterator.remove();
                }
            }

            for (Iterator<Particle> iterator = this.particles.iterator(); iterator.hasNext();) {
                Entity entity = iterator.next();
                entity.tick();
                if (entity.isRemoved()) {
                    iterator.remove();
                }
            }

            if (this.player != null) {
                this.data.tick();

                if (this.data.isDead()) {
                    this.data.save();
                    this.exit = true;
                }
            }
        }
    }

    public boolean isExiting() {
        return this.exit;
    }
}

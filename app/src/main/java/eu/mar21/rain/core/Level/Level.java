package eu.mar21.rain.core.level;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import eu.mar21.rain.core.entity.Entity;
import eu.mar21.rain.core.entity.item.Item;
import eu.mar21.rain.core.entity.mob.Mob;
import eu.mar21.rain.core.entity.mob.Player;
import eu.mar21.rain.core.entity.particle.Particle;
import eu.mar21.rain.core.entity.spawner.AcidSpawner;
import eu.mar21.rain.core.entity.spawner.ArmorSpawner;
import eu.mar21.rain.core.entity.spawner.EnergySpawner;
import eu.mar21.rain.core.entity.spawner.RainSpawner;
import eu.mar21.rain.core.entity.spawner.Spawner;
import eu.mar21.rain.core.entity.spawner.StarSpawner;
import eu.mar21.rain.core.graphics.Notification;
import eu.mar21.rain.core.level.data.PlayerData;
import eu.mar21.rain.core.utils.InputListener;
import eu.mar21.rain.core.utils.Resources;

public class Level {

    private final List<Entity> mobs = new ArrayList<>();
    private final List<Entity> particles = new ArrayList<>();
    private final List<Spawner> spawners = new ArrayList<>();

    private final List<Entity> buffer = Collections.synchronizedList(new ArrayList<Entity>());

    private InputListener input;
    private PlayerData data;

    private boolean exit = false;

    private Queue<Notification> notifications;

    public Level(InputListener input) {
        this.spawners.add(new RainSpawner(0, -20, Resources.SCREEN_WIDTH, 0, this, 1, 0, 5));
        this.input = input;

        this.notifications = new LinkedList<>();
    }

    public void reset() {
        this.exit = false;

        this.buffer.clear();
        this.mobs.clear();
        this.spawners.subList(1, this.spawners.size()).clear();
        for (Object e : this.particles.toArray()) {
            if (((Entity) e).isDead()) {
                this.particles.remove(e);
            }
        }

        this.data = new PlayerData(this);
        this.mobs.add(new Player((Resources.SCREEN_WIDTH - Resources.PLAYER[0].getWidth()) / 2, Resources.SCREEN_HEIGHT, this));

        this.spawners.add(new AcidSpawner(0, -50, Resources.SCREEN_WIDTH, 0, this, 20, 5, 2));
        this.spawners.add(new ArmorSpawner(0, -50, Resources.SCREEN_WIDTH, 0, this, (60 * 60) >> 1, 10 * 60, 1));
        this.spawners.add(new EnergySpawner(0, -50, Resources.SCREEN_WIDTH, 0, this, 60, 60, 1));
        this.spawners.add(new StarSpawner(0, -50, Resources.SCREEN_WIDTH, 0, this, 20 * 60, 0, 1));

        this.notifications.clear();
        showNotification(new Notification(Notification.NotificationStyle.PLAIN,"NEW GAME", null));
    }

    public void add(Entity e) {
        if (e instanceof Mob || e instanceof Item) {
            this.mobs.add(e);
        } else if (e instanceof Particle) {
            this.particles.add(e);
        } else if (e instanceof Spawner) {
            this.spawners.add((Spawner) e);
        }
    }

    public void showNotification(Notification notification) {
        this.notifications.add(notification);
    }

    public void addLater(Entity e) {
        this.buffer.add(e);
    }

    public InputListener getInput() {
        return this.input;
    }

    public PlayerData getData() {
        return this.data;
    }

    public void draw(Canvas c) {
        if (this.mobs.size() > 0) {
            for (int i = 0; i < 8; i++) {
                c.drawBitmap(Resources.BACKGROUND[i], (int) ((getPlayer().getCenterX() - Resources.SCREEN_WIDTH / 2) / (Resources.SCREEN_WIDTH / 8) * - Math.pow(1.55, i) - 100 / 2), 0, null);
            }
        } else {
            for (int i = 0; i < 8; i++) {
                c.drawBitmap(Resources.BACKGROUND[i], 0,0, null);
            }
        }

        for (Entity p : this.particles) {
            p.draw(c);
        }

        for (Entity m : this.mobs) {
            m.draw(c);
        }

        if (this.mobs.size() > 0) {
            this.data.draw(c);
        }

        if (!this.notifications.isEmpty()) {
            this.notifications.peek().draw(c);
        }
    }

    public List<Entity> getMobs() {
        return mobs.subList(1, mobs.size());
    }

    public Player getPlayer() {
        return (this.mobs.size() > 0) ? (Player) this.mobs.get(0) : null;
    }

    public boolean isCollidingPlayerAABB(Entity entity) {
        return null != getPlayer() && getPlayer().isCollidingAABB(entity);
    }

    public void tick() {
        for (Iterator<Spawner> iterator = this.spawners.iterator(); iterator.hasNext();) {
            Spawner spawner = iterator.next();
            spawner.tick();
            if (spawner.isDead()) {
                iterator.remove();
            }
        }

        for (Iterator<Entity> iterator = this.mobs.iterator(); iterator.hasNext();) {
            Entity entity = iterator.next();
            entity.tick();
            if (entity.isDead()) {
                iterator.remove();
            }
        }

        for (Iterator<Entity> iterator = this.particles.iterator(); iterator.hasNext();) {
            Entity entity = iterator.next();
            entity.tick();
            if (entity.isDead()) {
                iterator.remove();
            }
        }

        for (Entity e : buffer) {
            add(e);
        }
        buffer.clear();

        if (this.mobs.size() > 0) {
            this.data.tick();

            if (this.data.getPlayerHealth() <= 0) {
                this.data.save();
                this.exit = true;
            }
        }

        if (!this.notifications.isEmpty()) {
            this.notifications.peek().update();
            if (this.notifications.peek().isDead()) {
                this.notifications.remove();
            }
        }
    }

    public boolean isExiting() {
        return this.exit;
    }
}

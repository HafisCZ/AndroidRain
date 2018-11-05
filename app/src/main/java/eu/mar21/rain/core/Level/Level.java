package eu.mar21.rain.core.Level;

import android.content.ClipData;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import eu.mar21.rain.core.entity.Entity;
import eu.mar21.rain.core.entity.Item;
import eu.mar21.rain.core.entity.mob.Mob;
import eu.mar21.rain.core.entity.mob.Player;
import eu.mar21.rain.core.entity.particle.Particle;
import eu.mar21.rain.core.entity.spawner.RainSpawner;
import eu.mar21.rain.core.entity.spawner.Spawner;
import eu.mar21.rain.core.utils.Resources;

public class Level {

    private final List<Entity> mobs = new ArrayList<>();
    private final List<Entity> particles = new ArrayList<>();
    private final List<Spawner> spawners = new ArrayList<>();

    public Level() {
        this.spawners.add(new RainSpawner(0, -20, Resources.SCREEN_WIDTH, 0, this, 0, 0, 5));
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

    public void draw(Canvas c) {
        for (Entity p : this.particles) {
            p.draw(c);
        }

        for (Entity m : this.mobs) {
            m.draw(c);
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
        for (Spawner s : this.spawners) {
            s.tick();
        }

        for (Entity m : this.mobs) {
            m.tick();
        }

        for (Entity p : this.particles) {
            p.tick();
        }

        for (Object e : this.mobs.toArray()) {
            if (((Entity) e).isDead()) {
                this.mobs.remove(e);
            }
        }

        for (Object e : this.particles.toArray()) {
            if (((Entity) e).isDead()) {
                this.particles.remove(e);
            }
        }

        for (Object e : this.spawners.toArray()) {
            if (((Entity) e).isDead()) {
                this.spawners.remove(e);
            }
        }
    }
}

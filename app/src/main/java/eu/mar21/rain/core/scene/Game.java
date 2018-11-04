package eu.mar21.rain.core.scene;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import eu.mar21.rain.core.entity.Crate;
import eu.mar21.rain.core.entity.Entity;
import eu.mar21.rain.core.entity.Player;
import eu.mar21.rain.core.graphics.Renderer;
import eu.mar21.rain.core.utils.Input;
import eu.mar21.rain.core.utils.Level;
import eu.mar21.rain.core.utils.Resources;
import eu.mar21.rain.core.utils.Vec3d;

public class Game extends Scene {

    private List<Entity> entityList = new ArrayList<>();
    private Player player;

    private Random rnd = new Random();

    private int movesMade;

    private int map[][] = null;
    private int map_id = 0;

    public Bitmap getMapTile(int i, int j) {
        switch (map[i][j]) {
            case 1 : return Resources.TILE_WALL;
            case 3 : return Resources.TILE_GOAL;
            case 0 :
            default: return Resources.TILE_EMPTY;
        }
    }

    private void drawTileAt(int i, int j, Canvas c, int tw, int th) {
        c.drawBitmap(getMapTile(i, j), null, new Rect(i * tw, j * th, (i + 1) * tw, (j + 1) * th), null);
    }

    public Game(Renderer r) {
        super(r);

        reset();
    }

    @Override
    public void update(Scene s) {

    }

    public void reset() {
        entityList.clear();

        this.map = Level.getLevel(map_id);

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (this.map[i][j] == 2) {
                    entityList.add(new Crate(i, j));
                } else if (this.map[i][j] == 4) {
                    this.player = new Player(i, j);
                    entityList.add(this.player);
                }
            }
        }

        movesMade = 0;
    }

    private boolean isEmpty(Vec3d pos) {
        if (getEntityAt(pos) == null) {
            switch (this.map[(int) pos.x][(int) pos.y]) {
                case 0:
                case 2:
                case 3:
                case 4:
                    return true;
                default:
                    return false;
            }
        } else {
            return false;
        }
    }

    private Entity getEntityAt(Vec3d pos) {
        for (Entity e : entityList) {
            if (e.getPosition().equals(pos)) {
                return e;
            }
        }

        return null;
    }

    private boolean tryMoveTile(Entity e, Vec3d dir) {
        final Vec3d nv = e.getPosition().add(dir);

        boolean moved = false;

        if (isEmpty(nv)) {
            moved = true;
            e.move(dir);
        } else if (e instanceof Player) {
            Entity at = getEntityAt(nv);
            if (at instanceof Crate && isEmpty(nv.add(dir))) {
                moved = true;
                at.move(dir);
                e.move(dir);

                ((Crate) at).setTarget(map[(int) at.getPosition().x][(int) at.getPosition().y] == 3);
            }
        }

        boolean is = true;
        for (Entity entity : entityList) {
            if (entity instanceof Crate) {
                is &= ((Crate) entity).getTarget();
            }
        }

        if (is) {
            map_id++;
            renderer.setActiveOverlay(Victory.class);
        }

        return moved;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if(tryMoveTile(this.player, Input.getSwipeDirection(velocityX, velocityY, 5.0f))) {
            this.movesMade++;
        }

        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        renderer.setActiveOverlay(Pause.class);
    }

    @Override
    public void draw(Canvas c) {
        int tilew = renderer.getWidth() / map.length;
        int tileh = renderer.getHeight() / map[0].length;

        if (tilew > tileh) {
            tilew = tileh;
        } else if (tileh > tilew) {
            tileh = tilew;
        }

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                drawTileAt(i, j, c, tilew, tileh);
            }
        }

        for (Entity e : entityList) {
            e.update();
            e.draw(c, tilew, tileh);
        }

        c.drawText("Moves: " + movesMade, tilew, c.getHeight() - tileh, Resources.FONT);
    }

    @Override
    public void end() {

    }

    @Override
    public void begin() {

    }

}

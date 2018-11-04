package eu.mar21.rain.core.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import eu.mar21.rain.core.utils.Vec3d;

public class Entity {

    protected Vec3d position;
    protected Bitmap tile;

    public Entity(int x, int y, Bitmap tile) {
        this.position = new Vec3d(x, y);
        this.tile = tile;
    }

    public void update() {

    }

    public Vec3d getPosition() {
        return this.position;
    }

    public void move(Vec3d direction) {
        this.position = this.position.add(direction);
    }

    public void draw(Canvas c, int tilew, int tileh) {
        c.drawBitmap(this.tile, null, new Rect((int) this.position.x * tilew, (int)this.position.y * tileh, ((int)this.position.x + 1) * tilew, ((int)this.position.y + 1) * tileh), null);
    }

}

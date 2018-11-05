package eu.mar21.rain.core.graphics.sprite;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Sprite {

    private Bitmap bitmap;

    public Sprite(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void draw(Canvas c, int x, int y) {
        c.drawBitmap(bitmap, x, y, null);
    }

}

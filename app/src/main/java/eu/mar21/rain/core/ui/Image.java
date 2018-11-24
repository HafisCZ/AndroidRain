package eu.mar21.rain.core.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;

public class Image extends View {

    private Bitmap sprite;
    private Paint filter = new Paint();
    private boolean center = false;

    public Image(Bitmap sprite) {
        super(0, 0, 0, 0);
        this.sprite = sprite;
    }

    public Image setFilter(int color) {
        this.filter.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY));
        return this;
    }

    public Image center(boolean center) {
        this.center = center;
        return this;
    }

    public Image setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    @Override
    public void drawView(Canvas c, float x, float y, float w, float h) {
        if (center) {
            x -= this.sprite.getWidth() / 2.0f;
            y -= this.sprite.getHeight() / 2.0f;
        }
        c.drawBitmap(this.sprite, x, y, this.filter);
    }

}
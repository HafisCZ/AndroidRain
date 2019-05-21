package eu.mar21.rain.core.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Image extends View {

    // Params
    private Bitmap bitmap;
    private boolean center;

    // Constructor
    public Image(Bitmap bitmap) {
        super(0, 0, 0, 0);

        this.bitmap = bitmap;
        this.center = false;
    }

    public Image(View parent, Bitmap bitmap) {
        this(bitmap);

        parent.add(this);
    }

    // Methods
    public Image setForeground(Paint paint) {
        this.bg = paint;
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
    public void draw(Canvas c, float x, float y, float w, float h) {
        if (center) {
            x -= this.bitmap.getWidth() / 2.0f;
            y -= this.bitmap.getHeight() / 2.0f;
        }
        c.drawBitmap(this.bitmap, x, y, this.fg);
    }

}
package eu.mar21.rain.core.graphics;

import android.graphics.Canvas;

import eu.mar21.rain.core.utils.Resources;

public class Notification {

    // Params
    private final String title;

    private int alpha;

    // Constructor
    public Notification(String title) {
        this.title = title;
        this.alpha = 200;
    }

    public void draw(Canvas c, double x, double y) {
        this.alpha -= 2;
        if (this.alpha < 0) {
            this.alpha = 0;
        }

        Resources.PAINT_NOTIFICATION.setAlpha(this.alpha);
        c.drawText(this.title, (float) x - 5.0f, (float) y, Resources.PAINT_NOTIFICATION);
    }

    public boolean isRemoved() {
        return this.alpha <= 0;
    }

}

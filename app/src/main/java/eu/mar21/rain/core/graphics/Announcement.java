package eu.mar21.rain.core.graphics;

import android.graphics.Canvas;

import eu.mar21.rain.core.utils.Resources;

public class Announcement implements Drawable {

    // Params
    private int alpha;
    private int stage;
    private int ticks;
    private boolean removed;

    private final String label;
    private final String description;

    // Constructor
    public Announcement(String label, String description) {
        this.alpha = 0;
        this.stage = 0;
        this.ticks = 0;
        this.removed = false;
        this.label = label;
        this.description = description;
    }

    // Methods
    @Override
    public void draw(Canvas c) {
        Resources.PAINT_ANNOUNCEMENT.setAlpha(this.alpha);

        if (this.description == null) {
            c.drawText(this.label, (float) Resources.WIDTH / 2.0f, (float) Resources.HEIGHT / 5.0f, Resources.PAINT_ANNOUNCEMENT);
        } else {
            Resources.PAINT_ANNOUNCEMENT_2.setAlpha(this.alpha);
            c.drawText(this.label, (float) Resources.WIDTH / 2.0f, (float) Resources.HEIGHT / 5.0f - (float) Resources.HEIGHT / 50.0f, Resources.PAINT_ANNOUNCEMENT);
            c.drawText(this.description, (float) Resources.WIDTH / 2.0f, (float) Resources.HEIGHT / 5.0f + (float) Resources.HEIGHT / 50.0f, Resources.PAINT_ANNOUNCEMENT_2);
        }

        float y0 = (float) Resources.HEIGHT / 8.0f;
        float y1 = 1.5f * (float) (Resources.HEIGHT / 5.0 - y0) + y0;

        float x0 = (float) Resources.WIDTH / 4.0f;
        float x1 = 3.0f * x0;
        float sx = 2.0f * x0;
        float a = (float) this.alpha / 170.0f;

        if (this.stage == 0) {
            c.drawLine(x1 - a * sx, y0, x1, y0, Resources.PAINT_ANNOUNCEMENT);
            c.drawLine(x0, y1, x0 + a * sx, y1, Resources.PAINT_ANNOUNCEMENT);
        } else if (this.stage == 1) {
            c.drawLine(x0, y0, x1, y0, Resources.PAINT_ANNOUNCEMENT);
            c.drawLine(x0, y1, x1, y1, Resources.PAINT_ANNOUNCEMENT);
        } else {
            c.drawLine(x0, y0, x0 + a * sx, y0, Resources.PAINT_ANNOUNCEMENT);
            c.drawLine(x1 - a * sx, y1, x1, y1, Resources.PAINT_ANNOUNCEMENT);
        }

        if (this.stage == 0) {
            this.alpha += 3;
            if (this.alpha > 170) {
                this.alpha = 170;
                this.stage++;
            }
        } else if (this.stage == 1) {
            if (++this.ticks > 60) {
                this.stage++;
            }
        } else if (this.stage == 2) {
            this.alpha -= 3;
            if (this.alpha < 0) {
                this.alpha = 0;
                this.stage++;
            }
        } else {
            remove();
        }
    }

    public void remove() {
        this.removed = true;
    }

    public boolean isRemoved() {
        return this.removed;
    }



}

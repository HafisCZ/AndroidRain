package eu.mar21.rain.core.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

public class Notification implements Drawable {

    private final String title;
    private final String description;
    private final Bitmap icon;

    private int position = 0;
    private int time = 0;
    private int direction = 0;

    private static final int MAX_POS = 300;
    private static final int STAY = 2 * 60;

    private static final int Y_OFFSET = 20;
    private static final int Y_HEIGHT = 100;

    private static final Paint BACKGROUND = new Paint();
    private static final Paint FONT = new Paint();
    private static final Paint FONT_SMALL = new Paint();
    static {
        BACKGROUND.setColor(0x108FBC8F);
        FONT.setColor(0xA4FFFFFF);
        FONT.setTextSize(30);
        FONT.setTypeface(Typeface.MONOSPACE);
        FONT_SMALL.set(FONT);
        FONT_SMALL.setTextSize(20);
    }

    public Notification(String title, String description, Bitmap icon) {
        this.title = title;
        this.description = description;
        this.icon = icon;
    }

    @Override
    public void draw(Canvas c) {
        c.drawRect(c.getWidth() - this.position, Y_OFFSET, c.getWidth(), Y_OFFSET + Y_HEIGHT, BACKGROUND);
        c.drawText(this.title, c.getWidth() - this.position + Y_OFFSET, Y_OFFSET + 40, FONT);
        if (this.description != null) {
            c.drawText(this.description, c.getWidth() - this.position + Y_OFFSET, Y_OFFSET + 80, FONT_SMALL);
        }
    }

    public boolean isDead() {
        return this.position < 0;
    }

    public void update() {
        if (this.direction == 0) {
            this.position += 10;
            if (this.position >= MAX_POS) {
                this.position = MAX_POS;
                this.direction++;
            }
        } else if (this.direction == 1) {
            if (++this.time > STAY) {
                this.direction++;
            }
        } else {
            this.position -= 10;
        }
    }

}

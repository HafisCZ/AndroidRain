package eu.mar21.rain.core.graphics;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

public class Notification implements Drawable {

    private static final Paint PAINT_BG = new Paint();
    private static final Paint PAINT0 = new Paint();
    private static final Paint PAINT1 = new Paint();
    private static final Paint PAINT2 = new Paint();
    static {
        PAINT_BG.setColor(0x108FBC8F);

        PAINT0.setStyle(Paint.Style.STROKE);
        PAINT1.setStyle(Paint.Style.STROKE);
        PAINT2.setStyle(Paint.Style.STROKE);

        PAINT0.setColor(0);
        PAINT1.setColor(Color.GREEN);
        PAINT2.setColor(Color.YELLOW);

        PAINT1.setAlpha(0xA0);
        PAINT2.setAlpha(0xA0);
    }

    public enum NotificationStyle {

        PLAIN(PAINT_BG, PAINT0),
        GREEN(PAINT_BG, PAINT1),
        YELLOW(PAINT_BG, PAINT2);

        private final Paint paint;
        private final Paint bg;

        NotificationStyle(Paint bg, Paint paint) {
            this.paint = paint;
            this.bg = bg;
        }
    }

    private final String title;
    private final String description;
    private final NotificationStyle style;

    private int position = 0;
    private int time = 0;
    private int direction = 0;

    private static final int MAX_POS = 300;
    private static final int STAY = 2 * 60;

    private static final int Y_OFFSET = 20;
    private static final int Y_HEIGHT = 100;

    private static final Paint FONT = new Paint();
    private static final Paint FONT_SMALL = new Paint();
    static {
        FONT.setColor(0xA4FFFFFF);
        FONT.setTextSize(30);
        FONT.setTypeface(Typeface.MONOSPACE);
        FONT_SMALL.set(FONT);
        FONT_SMALL.setTextSize(20);
    }

    public Notification(NotificationStyle style, String title, String description) {
        this.title = title;
        this.description = description;
        this.style = style;
    }

    @Override
    public void draw(Canvas c) {
        c.drawRect(c.getWidth() - this.position, Y_OFFSET, c.getWidth(), Y_OFFSET + Y_HEIGHT, this.style.bg);
        c.drawRect(c.getWidth() - this.position, Y_OFFSET, c.getWidth(), Y_OFFSET + Y_HEIGHT, this.style.paint);

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

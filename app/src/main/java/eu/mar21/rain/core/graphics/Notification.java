package eu.mar21.rain.core.graphics;

import android.graphics.Canvas;
import android.graphics.Paint;

import eu.mar21.rain.core.utils.Resources;

public class Notification implements Drawable {

    public enum NotificationStyle {

        PLAIN(Resources.PAINT_108FBC8F, Resources.PAINT_0_STROKE),
        GREEN(Resources.PAINT_108FBC8F, Resources.PAINT_A000FF00_STROKE),
        YELLOW(Resources.PAINT_108FBC8F, Resources.PAINT_A0FFFF00_STROKE);

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

    public Notification(NotificationStyle style, String title, String description) {
        this.title = title;
        this.description = description;
        this.style = style;
    }

    @Override
    public void draw(Canvas c) {
        c.drawRect(c.getWidth() - this.position, Y_OFFSET, c.getWidth(), Y_OFFSET + Y_HEIGHT, this.style.bg);
        c.drawRect(c.getWidth() - this.position, Y_OFFSET, c.getWidth(), Y_OFFSET + Y_HEIGHT, this.style.paint);

        c.drawText(this.title, c.getWidth() - this.position + Y_OFFSET, Y_OFFSET + 40, Resources.PAINT_M_A4FFFFFF_0030);
        if (this.description != null) {
            c.drawText(this.description, c.getWidth() - this.position + Y_OFFSET, Y_OFFSET + 80, Resources.PAINT_M_A4FFFFFF_0020);
        }
    }

    public boolean isRemoved() {
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

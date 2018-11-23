package eu.mar21.rain.core.ui;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Panel extends View {

    private Paint paint = new Paint();
    {
        this.paint.setColor(0);
    }

    public Panel(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public Panel setBackground(int color) {
        this.paint.setColor(color);
        return this;
    }

    @Override
    public void drawView(Canvas c, float x, float y, float w, float h) {
        /*
            DEBUG CODE

            Paint pt = new Paint();
            pt.setColor(Color.GREEN);
            pt.setStyle(Paint.Style.STROKE);
            c.drawRect(x, y, x + w, y + h, pt);
        */

        c.drawRect(x, y, x + w, y + h, this.paint);
    }

}

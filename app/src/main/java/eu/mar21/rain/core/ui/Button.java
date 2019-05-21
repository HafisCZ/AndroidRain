package eu.mar21.rain.core.ui;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Button extends View {

    //Params
    private String text;

    // Constructor
    public Button(String text, float x, float y, float w, float h) {
        super(x, y, w, h);

        this.text = text;
    }

    public Button(View parent, String text, float x, float y, float w, float h) {
        this(text, x, y, w, h);

        parent.add(this);
    }

    // Methods
    public Button setForeground(Paint paint) {
        this.fg = paint;
        return this;
    }

    public Button setBackground(Paint paint) {
        this.bg = paint;
        return this;
    }

    public Button setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    @Override
    public void draw(Canvas c, float x, float y, float w, float h) {
        c.drawRect(x, y, x + w, y + h, this.bg);
        c.drawText(this.text, x + w / 2.0f, y + h / 2.0f + this.fg.getTextSize() / 2.0f, this.fg);
    }

}
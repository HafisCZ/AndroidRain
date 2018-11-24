package eu.mar21.rain.core.ui;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

import eu.mar21.rain.core.utils.Resources;

public class Button extends View {

    private String text;
    private Paint font = new Paint();
    private Paint background = new Paint();

    public Button(View parent, String text, float x, float y, float w, float h) {
        this(text, x, y, w, h);
        parent.addChild(this);
    }

    public Button(String text, float x, float y, float w, float h) {
        super(x, y, w, h);
        this.text = text;
    }

    public Button setFont(Typeface typeface, int color, float size) {
        this.font.setTypeface(typeface);
        this.font.setTextSize(size * (float) Resources.SCREEN_WIDTH);
        this.font.setTextAlign(Paint.Align.CENTER);
        this.font.setColor(color);
        return this;
    }

    public Button setBackground(int color) {
        this.background.setColor(color);
        return this;
    }

    public Button setColor(int color) {
        this.font.setColor(color);
        return this;
    }

    public Button setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    @Override
    public void drawView(Canvas c, float x, float y, float w, float h) {
        c.drawRect(x, y, x + w, y + h, this.background);
        c.drawText(this.text, x + w / 2.0f, y + h / 2.0f + this.font.getTextSize() / 2.0f, this.font);
    }

}
package eu.mar21.rain.core.ui;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

import eu.mar21.rain.core.graphics.Drawable;
import eu.mar21.rain.core.graphics.sprite.Sprite;

public class View implements Drawable {

    public static final Paint DEFAULT_BG = new Paint();
    public static final Paint DEFAULT_FONT = new Paint();
    static {
        DEFAULT_BG.setColor(0x0F8FBC8F);
        DEFAULT_FONT.setColor(0xF0FFFFFF);
    }

    private float x;
    private float y;

    private float w;
    private float h;

    private Sprite sprite;
    private Paint paint;

    private String text;
    private Paint font;

    public View(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public View setBackground(Paint paint) {
        this.paint = paint;

        return this;
    }

    public View setBackground(Sprite sprite) {
        this.sprite = sprite;

        return this;
    }

    public View setText(String text, Paint font) {
        this.text = text;
        this.font = font;

        return this;
    }

    public View resetFont() {
        this.font.setTextAlign(Paint.Align.CENTER);
        this.font.setTextSize(this.h * 0.8f);
        this.font.setTypeface(Typeface.MONOSPACE);

        return this;
    }

    public View clearText() {
        this.text = null;
        this.font = null;

        return this;
    }

    public View clearBackground() {
        this.paint = null;
        this.sprite = null;

        return this;
    }

    @Override
    public void draw(Canvas c) {
        if (this.sprite != null) {
            this.sprite.draw(c, (int) this.x , (int) this.y);
        } else if (this.paint != null) {
            c.drawRect((int) this.x, (int) this.y, (int) this.x + this.w, (int) this.y + this.h, this.paint);
        }

        if (this.text != null) {
            c.drawText(this.text, (int) this.x + this.w / 2.0f, (int) this.y + (this.h * 0.8f), this.font);
        }
    }

}

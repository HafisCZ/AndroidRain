package eu.mar21.rain.core.scene.menu;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.util.Log;

import eu.mar21.rain.core.graphics.Renderer;
import eu.mar21.rain.core.scene.Game;
import eu.mar21.rain.core.scene.Scene;
import eu.mar21.rain.core.utils.Input;
import eu.mar21.rain.core.utils.Resources;

public class Menu extends Scene {

    private static final Paint BACKGROUND = new Paint();
    private static final Paint FONT = new Paint();
    static {
        BACKGROUND.setColor(0x0F8FBC8F);
        FONT.setColor(0xA4FFFFFF);
        FONT.setTextSize(50);
        FONT.setTypeface(Typeface.MONOSPACE);
    }

    private final int lx;
    private final int rx;
    private final int bh;
    private final int sh;

    private Input input;

    public Menu(Renderer r) {
        super(r);

        this.input = new Input();

        this.lx = (int) (Resources.SCREEN_WIDTH / 10.0);
        this.rx = (int) (Resources.SCREEN_WIDTH / 2.0);
        this.sh = (int) (Resources.SCREEN_HEIGHT / 10.0);
        this.bh = (int) (3.0 * Resources.SCREEN_HEIGHT / 20.0);
    }

    private void drawButton(Canvas c, String text, int row) {
        c.drawRect(this.lx, this.sh + row * this.bh + 10 * row, this.rx, this.sh + (row + 1) * this.bh + 10 * row, BACKGROUND);
        c.drawText(text, this.rx / 3, this.sh + row * (this.bh + 10) + 50 + 25, FONT);
    }

    private boolean isButtonDown(int row) {
        return this.input.inside(this.lx, this.sh + row * this.bh + 10 * row, this.rx, this.sh + (row + 1) * this.bh + 10 * row);
    }

    @Override
    public void draw(Canvas c) {
        for (int i = 0; i < Resources.BACKGROUND.length; i++) {
            c.drawBitmap(Resources.BACKGROUND[i], - 50, 0, null);
        }

        drawButton(c, "PLAY", 0);
        drawButton(c, "UPGRADES", 1);
        drawButton(c, "STATISTICS", 2);
        drawButton(c, "SETTINGS", 3);
        drawButton(c, "EXIT", 4);
    }

    @Override
    public void update(Scene s) {
        if (isButtonDown(0)) {
            this.renderer.requestScene(Game.class);
        }

        if (isButtonDown(1)) {

        }

        if (isButtonDown(2)) {

        }

        if (isButtonDown(3)) {

        }

        if (isButtonDown(4)) {
            this.renderer.getParentActivity().finish();
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void begin() {

    }

    @Override
    public void end() {

    }

    @Override
    public Input getDedicatedListener() {
        return this.input;
    }

}

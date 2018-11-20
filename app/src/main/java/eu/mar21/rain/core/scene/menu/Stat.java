package eu.mar21.rain.core.scene.menu;

import android.graphics.Canvas;

import eu.mar21.rain.core.graphics.Renderer;
import eu.mar21.rain.core.level.data.Statistics;
import eu.mar21.rain.core.scene.Scene;
import eu.mar21.rain.core.ui.View;
import eu.mar21.rain.core.utils.input.InputListener;
import eu.mar21.rain.core.utils.Resources;

public class Stat extends Scene {

    private InputListener input;

    public Stat(Renderer r) {
        super(r);

        this.input = new InputListener();
    }

    @Override
    public void update(Scene s) {
        if (this.input.isHeld(InputListener.ControlZone.WHOLE_SCREEN)) {
            this.renderer.requestScene(Menu.class);
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void begin() {
        this.input.reset();
    }

    @Override
    public void end() {

    }

    @Override
    public InputListener getDedicatedListener() {
        return this.input;
    }

    private void drawItem(Canvas c, int row, int col, String label, int value) {
        c.drawText(label, (float) Resources.SCREEN_WIDTH / 20.0f + col * (float) Resources.SCREEN_WIDTH / 2.0f, (float) Resources.SCREEN_HEIGHT / 10.0f * row + (float) Resources.SCREEN_HEIGHT / 10.0f, Resources.FONT_DEBUG);
        c.drawText(Integer.toString(value), (float) Resources.SCREEN_WIDTH / 3.0f + col * (float) Resources.SCREEN_WIDTH / 2.0f, (float) Resources.SCREEN_HEIGHT / 10.0f * row + (float) Resources.SCREEN_HEIGHT / 10.0f, Resources.FONT_DEBUG);
    }

    @Override
    public void draw(Canvas c) {
        for (int i = 0; i < Resources.BACKGROUND.length; i++) {
            c.drawBitmap(Resources.BACKGROUND[i], - 50, 0, null);
        }

        drawItem(c, 0, 0, "Player level:", Statistics.PLAYER_LEVEL.get());
        drawItem(c, 1, 0, "Total score:", Statistics.PLAYER_SCORE.get());

        new View(0, 0, (float) Resources.SCREEN_WIDTH,(float) Resources.SCREEN_HEIGHT / 10.0f).setBackground(View.DEFAULT_BG).setText("STATISTICS", View.DEFAULT_FONT).resetFont().draw(c);
    }
}

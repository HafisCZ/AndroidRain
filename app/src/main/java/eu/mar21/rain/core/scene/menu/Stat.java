package eu.mar21.rain.core.scene.menu;

import android.graphics.Canvas;

import eu.mar21.rain.core.graphics.Renderer;
import eu.mar21.rain.core.level.data.Statistics;
import eu.mar21.rain.core.scene.Scene;
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

    @Override
    public void draw(Canvas c) {
        for (int i = 0; i < 13; i++) {
            c.drawText(Statistics.values()[i].name(), 50, 50 * i + 50, Resources.FONT_DEBUG);
            c.drawText(Statistics.values()[i].get() + "", 400, 50 * i + 50, Resources.FONT_DEBUG);
        }

        for (int i = 13; i < Statistics.values().length; i++) {
            c.drawText(Statistics.values()[i].name(), 50 + 700, 50 * (i - 13) + 50, Resources.FONT_DEBUG);
            c.drawText(Statistics.values()[i].get() + "", 400 + 700, 50 * (i - 13) + 50, Resources.FONT_DEBUG);
        }
    }
}

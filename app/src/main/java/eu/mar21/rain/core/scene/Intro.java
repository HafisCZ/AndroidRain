package eu.mar21.rain.core.scene;

import android.graphics.Canvas;

import eu.mar21.rain.core.graphics.Window;
import eu.mar21.rain.core.utils.Logger;
import eu.mar21.rain.core.utils.Resources;

public class Intro extends Scene {

    // Params
    private int alpha;
    private boolean fade;

    // Constructor
    public Intro(Window w) {
        super(w);
    }

    // Methods
    @Override
    public void begin() {
        this.alpha = 0;
        this.fade = false;
    }

    @Override
    public void update(Scene s) {
        if (this.fade) {
            this.alpha -= 4;
            if (this.alpha < 0) {
                this.alpha = 0;
                this.fade = false;
            }
        } else {
            this.alpha += 2;
            if (this.alpha > 255) {
                this.alpha = 255;
                this.fade = true;
            }
        }
    }

    @Override
    public void draw(Canvas c) {
        Resources.PAINT_ALPHA.setAlpha(this.alpha);

        c.drawBitmap(Resources.LOGO, (float) (c.getWidth() / 2 - Resources.LOGO.getWidth() / 2), (float) (c.getHeight() / 2 - Resources.LOGO.getHeight() / 2), Resources.PAINT_ALPHA);

        Logger.draw(c);
    }
}

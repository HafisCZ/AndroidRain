package eu.mar21.rain.core.scene;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import eu.mar21.rain.core.graphics.Renderer;
import eu.mar21.rain.core.utils.Resources;

public class Intro extends Scene {

    private int alpha;
    private boolean fadeOut;

    @SuppressWarnings("unused")
    public Intro(Renderer r) {
        super(r);
    }

    @Override
    public void update(Scene s) {
        if (this.fadeOut) {
            this.alpha -= 3;
            if (this.alpha < 0) {
                this.alpha = 0;

                renderer.setActiveOverlay(null);
            }
        } else {
            this.alpha += 2;
            if (this.alpha > 255) {
                this.alpha = 255;
                this.fadeOut = true;

                renderer.setActiveScene(Game.class);
            }
        }
    }

    @Override
    public void begin() {
        this.alpha = 0;
        this.fadeOut = false;
    }

    @Override
    public void end() {

    }

    @Override
    public void draw(Canvas c) {
        Resources.ALPHA_ONLY_PAINT.setAlpha(this.alpha);

        final Bitmap logo = Resources.LOGO;
        c.drawBitmap(logo, c.getWidth() / 2 - logo.getWidth()/ 2, c.getHeight() / 2 - logo.getHeight() / 2, Resources.ALPHA_ONLY_PAINT);
    }
}

package eu.mar21.rain.core.scene.menu;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import eu.mar21.rain.core.graphics.Renderer;
import eu.mar21.rain.core.level.data.Statistics;
import eu.mar21.rain.core.scene.Scene;
import eu.mar21.rain.core.ui.Panel;
import eu.mar21.rain.core.ui.Text;
import eu.mar21.rain.core.ui.View;
import eu.mar21.rain.core.utils.input.InputListener;
import eu.mar21.rain.core.utils.Resources;

public class Stat extends Scene {

    private InputListener input;

    private View view;

    public Stat(Renderer r) {
        super(r);

        this.input = new InputListener();
    }

    private Panel newInfo(String s1, int s2, float x, float y) {
        Panel panel = new Panel(x, y, 0.4f, 0.1f);
        panel.addChild(new Text(s1).setPosition(0, 0.8f).setFont(Typeface.MONOSPACE, Color.WHITE, 0.02f, Paint.Align.LEFT));
        panel.addChild(new Text(Integer.toString(s2)).setPosition(1, 0.8f).setFont(Typeface.MONOSPACE, Color.WHITE, 0.02f, Paint.Align.RIGHT));

        return panel;
    }

    @Override
    public void begin() {
        this.input.reset();

        this.view = new Panel(0, 0, 1, 1);

        /*
            Header & back button
        */
        Panel title = (Panel) new Panel(0, 0, 1, 0.1f).setBackground(0x0F8FBC8F).onClick(v -> renderer.requestScene(Menu.class));
        title.addChild(new Text("STATISTICS").setPosition(0.5f, 0.8f).setFont(Typeface.MONOSPACE, Color.WHITE,0.05f, Paint.Align.CENTER));
        title.addChild(new Text("<<").setPosition(0.01f, 0.8f).setFont(Typeface.MONOSPACE, Color.LTGRAY, 0.05f, Paint.Align.LEFT));
        this.view.addChild(title);

        /*
            Entries
        */
        this.view.addChild(newInfo("Level", Statistics.PLAYER_LEVEL.get(), 0.05f, 0.15f));
        this.view.addChild(newInfo("Score", Statistics.PLAYER_SCORE.get(), 0.05f, 0.25f));
        this.view.addChild(newInfo("Total EXP", Statistics.STAT_COUNT_EXP.get(), 0.05f, 0.35f));

        this.view.addChild(newInfo("Nodes", Statistics.STAT_COUNT_NODES.get(), 0.55f, 0.15f));
        this.view.addChild(newInfo("Shields", Statistics.STAT_COUNT_SHIELD.get(), 0.55f, 0.25f));
        this.view.addChild(newInfo("Stars", Statistics.STAT_COUNT_STARS.get(), 0.55f, 0.35f));
        this.view.addChild(newInfo("Skills used", Statistics.STAT_COUNT_ACTIVATE.get(), 0.55f, 0.45f));
        this.view.addChild(newInfo("Jumps", Statistics.STAT_COUNT_JUMP.get(), 0.55f, 0.55f));
        this.view.addChild(newInfo("Damage", Statistics.STAT_COUNT_DAMAGE.get(), 0.55f, 0.65f));

        this.view.setListener(this.input);
    }

    @Override
    public InputListener getDedicatedListener() {
        return this.input;
    }

    @Override
    public void draw(Canvas c) {
        for (int i = 0; i < Resources.BACKGROUND.length; i++) {
            c.drawBitmap(Resources.BACKGROUND[i], - 50, 0, null);
        }

        this.view.draw(c);
    }
}

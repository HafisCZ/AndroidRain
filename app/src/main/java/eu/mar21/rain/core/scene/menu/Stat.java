package eu.mar21.rain.core.scene.menu;

import android.graphics.Canvas;

import eu.mar21.rain.core.device.input.InputListener;
import eu.mar21.rain.core.graphics.Window;
import eu.mar21.rain.core.level.data.Statistics;
import eu.mar21.rain.core.scene.Scene;
import eu.mar21.rain.core.ui.Panel;
import eu.mar21.rain.core.ui.Text;
import eu.mar21.rain.core.utils.Resources;

public class Stat extends Scene {

    // Constructor
    public Stat(Window w) {
        super(w);
    }

    // Methods
    private Panel entry(String s, int i, float x, float y) {
        Panel p = new Panel(x, y, 0.4f, 0.1f).setBackground(Resources.PAINT_0);

        p.add(new Text(s).setPosition(0, 0.8f).setForeground(Resources.PAINT_M_W_0020_L));
        p.add(new Text(Integer.toString(i)).setPosition(1, 0.8f).setForeground(Resources.PAINT_M_W_0020_R));

        return p;
    }

    @Override
    public void begin() {
        this.view = new Panel(0, 0, 1, 1).setBackground(Resources.PAINT_0);

        Panel title = (Panel) new Panel(0, 0, 1, 0.1f).setBackground(Resources.PAINT_0F8FBC8F).onClick(v -> window.request(Menu.class));
        title.add(new Text("STATISTICS").setPosition(0.5f, 0.8f).setForeground(Resources.PAINT_M_W_0050_C));
        title.add(new Text("<<").setPosition(0.01f, 0.8f).setForeground(Resources.PAINT_M_LGRAY_0050_L));
        this.view.add(title);

        this.view.add(entry("Level", Statistics.PLAYER_LEVEL.get(), 0.05f, 0.15f));
        this.view.add(entry("Score", Statistics.PLAYER_SCORE.get(), 0.05f, 0.25f));
        this.view.add(entry("Total EXP", Statistics.STAT_TOTAL_EXP.get(), 0.05f, 0.35f));
        this.view.add(entry("Longest game", Statistics.STAT_LONGEST_GAME.get(), 0.05f, 0.45f));

        this.view.add(entry("Nodes", Statistics.STAT_ENERGY_COLLECTED.get(), 0.55f, 0.15f));
        this.view.add(entry("Shields", Statistics.STAT_SHIELDS_COLLECTED.get(), 0.55f, 0.25f));
        this.view.add(entry("Stars", Statistics.STAT_RANDOM_COLLECTED.get(), 0.55f, 0.35f));
        this.view.add(entry("Skills used", Statistics.STAT_SKILL_ACTIVATIONS.get(), 0.55f, 0.45f));
        this.view.add(entry("Jumps", Statistics.STAT_TOTAL_JUMPS.get(), 0.55f, 0.55f));
        this.view.add(entry("Damage", Statistics.STAT_DMG_TAKEN.get(), 0.55f, 0.65f));

        this.view.setListener(this.window.getListener());
    }

    @Override
    public void draw(Canvas c) {
        for (int i = 0; i < Resources.BACKGROUND.length; i++) {
            c.drawBitmap(Resources.BACKGROUND[i], - 50, 0, null);
        }

        this.view.show(c);
    }
}

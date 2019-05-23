package eu.mar21.rain.core.scene.menu;

import android.graphics.Canvas;

import eu.mar21.rain.core.graphics.Window;
import eu.mar21.rain.core.level.data.Data;
import eu.mar21.rain.core.scene.Scene;
import eu.mar21.rain.core.ui.Button;
import eu.mar21.rain.core.ui.List;
import eu.mar21.rain.core.ui.Panel;
import eu.mar21.rain.core.ui.Text;
import eu.mar21.rain.core.ui.View;
import eu.mar21.rain.core.utils.Resources;

public class Stat extends Scene {

    // Constructor
    public Stat(Window w) {
        super(w);
    }

    // Methods
    private void entry(View v, String label, int var, float col, float row) {
        Panel p = new Panel(v, 0.05f + col * 0.5f, 0.15f + row * 0.1f, 0.4f, 0.1f).setBackground(Resources.PAINT_0);
        new Text(p, label).setPosition(0, 0.8f).setForeground(Resources.PAINT_M_W_0020_L);
        new Text(p, Integer.toString(var)).setPosition(1, 0.8f).setForeground(Resources.PAINT_M_W_0020_R);
    }

    private void entry(View v, Data data, float col, float row) {
        Panel p = new Panel(v, 0.05f + col * 0.5f, 0.07f + row * 0.13f, 0.4f, 0.12f).setBackground(data.get() > 0 ? Resources.PAINT_2F8FBC8F : Resources.PAINT_0);
        new Text(p, data.getLabel()).setPosition(0.02f, 0.4f).setForeground(Resources.PAINT_M_W_0020_L);
        new Text(p, data.getDescription()).setPosition(0.02f, 0.8f).setForeground(Resources.PAINT_M_LGRAY_0015_L);
    }

    @Override
    public void begin() {
        this.view = new Panel(0, 0, 1, 1).setBackground(Resources.PAINT_0);

        Panel title = (Panel) new Panel(this.view, 0, 0, 1, 0.1f).setBackground(Resources.PAINT_0F8FBC8F).onClick(v -> window.request(Menu.class));
        new Text(title, "STATISTICS").setPosition(0.5f, 0.8f).setForeground(Resources.PAINT_M_W_0050_C);
        new Text(title, "<<").setPosition(0.01f, 0.8f).setForeground(Resources.PAINT_M_LGRAY_0050_L);

        List list = new List(this.view, 0, 0.1f, 1, 0.9f);
        Panel p0 = list.add().setBackground(Resources.PAINT_0);
        Panel p1 = list.add().setBackground(Resources.PAINT_0);
        Panel p2 = list.add().setBackground(Resources.PAINT_0);

        new Button(this.view, "NEXT", 0.85f, 0.9f, 0.15f, 0.1f).setBackground(Resources.PAINT_0F8FBC8F).setForeground(Resources.PAINT_M_W_0030_C).onClick(v -> list.next());
        new Button(this.view, "PREV", 0, 0.9f, 0.15f, 0.1f).setBackground(Resources.PAINT_0F8FBC8F).setForeground(Resources.PAINT_M_W_0030_C).onClick(v -> list.prev());

        entry(p0, "Level", Data.PLAYER_LEVEL.get(), 0, 0);
        entry(p0, "Score", Data.PLAYER_SCORE.get(), 0, 1);
        entry(p0, "Experience", Data.STAT_TOTAL_EXP.get(), 0, 2);
        entry(p0, "Longest game", Data.STAT_LONGEST_GAME.get(), 0, 3);
        entry(p0, "Skills used", Data.STAT_SKILL_ACTIVATIONS.get(), 0, 4);
        entry(p0, "Nodes", Data.STAT_ENERGY_COLLECTED.get(), 1, 0);
        entry(p0, "Shields", Data.STAT_SHIELDS_COLLECTED.get(), 1, 1);
        entry(p0, "Stars", Data.STAT_RANDOM_COLLECTED.get(), 1, 2);
        entry(p0, "Jumps", Data.STAT_TOTAL_JUMPS.get(), 1, 3);
        entry(p0, "Damage", Data.STAT_DMG_TAKEN.get(), 1, 4);
        entry(p0, "Lightning hits", Data.STAT_LIGHTNING_HIT.get(), 1, 5);

        entry(p1, Data.ACH_LEVEL1, 0, 0);
        entry(p1, Data.ACH_LEVEL5, 0, 2);
        entry(p1, Data.ACH_LEVEL10, 0, 3);
        entry(p1, Data.ACH_LEVEL25, 0, 4);
        entry(p1, Data.ACH_LEVEL50, 0, 5);
        entry(p1, Data.ACH_SCORE100K, 1, 0);
        entry(p1, Data.ACH_MAXHEALTH, 1, 2);

        entry(p2, Data.ACH_MAXARMOR, 0, 0);
        entry(p2, Data.ACH_JUMP1K, 0, 1);
        entry(p2, Data.ACH_LIGHTNINGROD0, 0, 3);
        entry(p2, Data.ACH_LIGHTNINGROD1, 0, 4);
        entry(p2, Data.ACH_NODE1K, 1, 0);
        entry(p2, Data.ACH_STAR1K, 1, 1);
        entry(p2, Data.ACH_SHIELD1K, 1, 2);
        entry(p2, Data.ACH_DAMAGE1K, 1, 3);

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

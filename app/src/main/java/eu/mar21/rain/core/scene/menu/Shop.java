package eu.mar21.rain.core.scene.menu;

import android.graphics.Canvas;

import java.util.ArrayList;

import eu.mar21.rain.core.graphics.Window;
import eu.mar21.rain.core.level.data.Award;
import eu.mar21.rain.core.level.data.Data;
import eu.mar21.rain.core.level.data.Upgrade;
import eu.mar21.rain.core.scene.Scene;
import eu.mar21.rain.core.ui.Button;
import eu.mar21.rain.core.ui.Image;
import eu.mar21.rain.core.ui.List;
import eu.mar21.rain.core.ui.Panel;
import eu.mar21.rain.core.ui.Text;
import eu.mar21.rain.core.ui.View;
import eu.mar21.rain.core.utils.Resources;
import eu.mar21.rain.core.utils.functional.Tuple;

public class Shop extends Scene {

    // Params
    private final java.util.List<Tuple<Upgrade, Panel>> panels = new ArrayList<>();
    private Text points;

    // Constructor
    public Shop(Window w) {
        super(w);
    }

    // Methods
    private void entry(View v, Upgrade base, float col, float row) {
        Upgrade upgrade = base.get();

        Panel p = new Panel(v, 0.05f + col * 0.5f, 0.07f + row * 0.13f, 0.4f, 0.12f);
        p.onClick(vv -> {
            if (upgrade.tryBuy() != null) {
                Upgrade.save();
                begin();
            }
        });

        if (upgrade.isOwned()) {
            p.setBackground(Resources.PAINT_0FF4F442);
        } else if (upgrade.isAvailable()) {
            p.setBackground(Resources.PAINT_2F8FBC8F);
        } else {
            p.setBackground(Resources.PAINT_0F8FBC8F);
        }

        new Text(p, "" + upgrade.getCost()).setPosition(0.99f, 0.4f).setForeground(Resources.PAINT_M_W_0020_R);
        new Text(p, upgrade.getLabel()).setPosition(0.02f, 0.4f).setForeground(Resources.PAINT_M_W_0020_L);
        new Text(p, upgrade.getDescription()).setPosition(0.02f, 0.8f).setForeground(Resources.PAINT_M_LGRAY_0015_L);

        this.panels.add(new Tuple<>(base, p));
    }

    private void entryUpdate(Upgrade upgrade, Panel p) {
        p.get().clear();
        new Text(p, upgrade.getLabel()).setPosition(0.02f, 0.4f).setForeground(Resources.PAINT_M_W_0020_L);
        new Text(p, upgrade.getDescription()).setPosition(0.02f, 0.8f).setForeground(Resources.PAINT_M_LGRAY_0015_L);

        if (upgrade.isOwned()) {
            p.setBackground(Resources.PAINT_0FF4F442);
        } else if (upgrade.isAvailable()) {
            new Text(p, "" + upgrade.getCost()).setPosition(0.99f, 0.4f).setForeground(Resources.PAINT_M_W_0020_R);
            p.setBackground(Resources.PAINT_2F8FBC8F);
        } else {
            new Text(p, "" + upgrade.getCost()).setPosition(0.99f, 0.4f).setForeground(Resources.PAINT_M_W_0020_R);
            p.setBackground(Resources.PAINT_0F8FBC8F);
        }
    }

    @Override
    public void init() {
        this.view = new Panel(0, 0, 1, 1).setBackground(Resources.PAINT_0);

        Panel title = (Panel) new Panel(this.view, 0, 0, 1, 0.1f).setBackground(Resources.PAINT_0F8FBC8F).onClick(v -> window.request(Menu.class));
        new Text(title, "SHOP").setPosition(0.5f, 0.8f).setForeground(Resources.PAINT_M_W_0050_C);
        new Text(title, "<<").setPosition(0.01f, 0.8f).setForeground(Resources.PAINT_M_LGRAY_0050_L);

        this.points = new Text(title, "" + Data.PLAYER_POINTS.get()).setPosition(0.95f, 0.7f).setForeground(Resources.PAINT_M_Y_0030_C);

        List list = new List(this.view, 0, 0.1f, 1, 0.9f);
        Panel l0 = list.add().setBackground(Resources.PAINT_0);
        Panel l1 = list.add().setBackground(Resources.PAINT_0);

        new Button(this.view, "NEXT", 0.85f, 0.9f, 0.15f, 0.1f).setBackground(Resources.PAINT_0F8FBC8F).setForeground(Resources.PAINT_M_W_0030_C).onClick(v -> list.next());
        new Button(this.view, "PREV", 0, 0.9f, 0.15f, 0.1f).setBackground(Resources.PAINT_0F8FBC8F).setForeground(Resources.PAINT_M_W_0030_C).onClick(v -> list.prev());

        entry(l0, Upgrade.HEALTH_EXTRA, 0, 0);
        entry(l0, Upgrade.BATTERY, 0, 2);
        entry(l0, Upgrade.BATTERY_LIGHTNING, 0, 3);
        entry(l0, Upgrade.SKILL_SHOCK, 1, 0);
        entry(l0, Upgrade.SKILL_ARMOR, 1, 1);
        entry(l0, Upgrade.SKILL_EXP, 1, 2);
        entry(l0, Upgrade.SKILL_LIGHTNING_POLE, 1, 3);

        entry(l1, Upgrade.STAR_XP_BOOST, 0, 0);
        entry(l1, Upgrade.STAR_XP_INSTANT, 0, 1);
        entry(l1, Upgrade.STAR_ARMOR, 0, 2);
        entry(l1, Upgrade.STAR_POINT, 0, 3);

        this.view.setListener(this.window.getListener());
    }

    @Override
    public void begin() {
        for (Tuple<Upgrade, Panel> p : this.panels) {
            entryUpdate(p.first.get(), p.second);
        }

        this.points.setText("" + Data.PLAYER_POINTS.get());
    }

    @Override
    public void draw(Canvas c) {
        for (int i = 0; i < Resources.BACKGROUND.length; i++) {
            c.drawBitmap(Resources.BACKGROUND[i], - 50, 0, null);
        }

        this.view.show(c);
    }

}

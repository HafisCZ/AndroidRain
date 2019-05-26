package eu.mar21.rain.core.scene.menu;

import android.graphics.Canvas;

import eu.mar21.rain.core.graphics.Window;
import eu.mar21.rain.core.level.data.Data;
import eu.mar21.rain.core.scene.Scene;
import eu.mar21.rain.core.ui.Button;
import eu.mar21.rain.core.ui.Image;
import eu.mar21.rain.core.ui.List;
import eu.mar21.rain.core.ui.Panel;
import eu.mar21.rain.core.ui.Text;
import eu.mar21.rain.core.utils.Resources;

public class Shop extends Scene {

    // Params
    private Panel title;
    private Panel panels;
    private Panel[] skills = new Panel[5];
    private Button[] stars = new Button[4];

    // Constructor
    public Shop(Window w) {
        super(w);
    }

    // Methods
    @Override
    public void init() {
        this.view = new Panel(0, 0, 1, 1).setBackground(Resources.PAINT_0);

        this.title = (Panel) new Panel(this.view, 0, 0, 1, 0.1f).setBackground(Resources.PAINT_0F8FBC8F).onClick(v -> window.request(Menu.class));
        new Text(this.title, "SHOP").setPosition(0.5f, 0.8f).setForeground(Resources.PAINT_M_W_0050_C);
        new Text(this.title, "<<").setPosition(0.01f, 0.8f).setForeground(Resources.PAINT_M_LGRAY_0050_L);

        List list = new List(this.view, 0, 0.1f, 1, 0.9f);
        Panel listItem0 = list.add().setBackground(Resources.PAINT_0);
        Panel listItem1 = list.add().setBackground(Resources.PAINT_0);

        new Button(this.view, "NEXT", 0.85f, 0.9f, 0.15f, 0.1f).setBackground(Resources.PAINT_0F8FBC8F).setForeground(Resources.PAINT_M_W_0030_C).onClick(v -> list.next());
        new Button(this.view, "PREV", 0, 0.9f, 0.15f, 0.1f).setBackground(Resources.PAINT_0F8FBC8F).setForeground(Resources.PAINT_M_W_0030_C).onClick(v -> list.prev());

        new Text(listItem0, "GENERAL").setPosition(0.5f, 0.1f).setForeground(Resources.PAINT_M_W_0030_C);
        new Text(listItem1, "STAR UPGRADES").setPosition(0.5f, 0.1f).setForeground(Resources.PAINT_M_W_0030_C);

        String[] starsLabels = { "INSTANT XP", "SHIELD", "XP BOOST", "FREE POINT" };
        for (int i = 0; i < 4; i++) {
            this.stars[i] = (Button) new Button(listItem1, starsLabels[i], 0.05f + 0.25f * i, 0.2f, 0.15f, 0.15f).setBackground(Resources.PAINT_0).setForeground(Resources.PAINT_M_W_0030_C).onClick(v -> {
                if (Data.PLAYER_POINTS.get() > 0 && Data.UPGRADE_RANDOM.get() < 5) {
                    Data.PLAYER_POINTS.add(-1);
                    Data.UPGRADE_RANDOM.add(1);
                    Data.save();

                    begin();
                }
            });
        }

        panels = new Panel(listItem0, 0.1f, 0.15f, 0.8f, 0.1f).setBackground(Resources.PAINT_0);

        for (int i = 0; i < 5; i++) {
            skills[i] = new Panel(listItem0, 0.1f + 0.3f * (i % 3), 0.5f + (float) (i / 3) * 0.25f, 0.2f, 0.2f * listItem0.getScale()).setBackground(Resources.PAINT_0);
        }

        panels.onClick(v -> {
            if (Data.PLAYER_POINTS.get() > 0) {
                if (Data.PLAYER_HEALTH.get() < 10) {
                    Data.PLAYER_POINTS.add(-1);
                    Data.PLAYER_HEALTH.add(1);
                    Data.save();

                    begin();
                }
            }
        });

        skills[0].onClick(v -> {
            if (Data.PLAYER_POINTS.get() > 0) {
                if (Data.UPGRADE_SKILL_SHOCK.get() < 1) {
                    Data.PLAYER_POINTS.add(-1);
                    Data.UPGRADE_SKILL_SHOCK.add(1);
                    Data.save();

                    begin();
                }
            }
        });

        skills[1].onClick(v -> {
            if (Data.PLAYER_POINTS.get() > 0) {
                if (Data.UPGRADE_SKILL_SHIELD.get() < 1) {
                    Data.PLAYER_POINTS.add(-1);
                    Data.UPGRADE_SKILL_SHIELD.add(1);
                    Data.save();

                    begin();
                }
            }
        });

        skills[2].onClick(v -> {
            if (Data.PLAYER_POINTS.get() > 0) {
                if (Data.UPGRADE_SKILL_XPBOOST.get() < 1) {
                    Data.PLAYER_POINTS.add(-1);
                    Data.UPGRADE_SKILL_XPBOOST.add(1);
                    Data.save();

                    begin();
                }
            }
        });

        skills[3].onClick(v -> {
            if (Data.PLAYER_POINTS.get() > 0) {
                if (Data.UPGRADE_ENERGY_MULT.get() < 1) {
                    Data.PLAYER_POINTS.add(-1);
                    Data.UPGRADE_ENERGY_MULT.add(1);
                    Data.save();

                    begin();
                }
            }
        });

        skills[4].onClick(v -> {
            if (Data.PLAYER_POINTS.get() > 0) {
                if (Data.UPGRADE_BATTERY.get() < 3) {
                    Data.PLAYER_POINTS.add(-1);
                    Data.UPGRADE_BATTERY.add(1);
                    Data.save();

                    begin();
                }
            }
        });

        new Text(panels, "HEALTH").setPosition(0.1f, 0.8f).setForeground(Resources.PAINT_M_W_0030_C);

        new Text(skills[0], "SHOCK").setPosition(0.5f, 0.2f).setForeground(Resources.PAINT_M_W_0030_C);
        new Text(skills[1], "BONUS SHIELD").setPosition(0.5f, 0.2f).setForeground(Resources.PAINT_M_W_0030_C);
        new Text(skills[2], "XP BOOST").setPosition(0.5f, 0.2f).setForeground(Resources.PAINT_M_W_0030_C);
        new Text(skills[3], "DOUBLE POWER").setPosition(0.5f, 0.2f).setForeground(Resources.PAINT_M_W_0030_C);
        new Text(skills[4], "BATTERY").setPosition(0.5f, 0.2f).setForeground(Resources.PAINT_M_W_0030_C);

        new Image(skills[0], Resources.SKILLS[0]).setPosition(0.5f, 0.7f).center(true);
        new Image(skills[1], Resources.SKILLS[1]).setPosition(0.5f, 0.7f).center(true);
        new Image(skills[2], Resources.SKILLS[2]).setPosition(0.5f, 0.7f).center(true);

        this.view.setListener(this.window.getListener());
    }

    @Override
    public void begin() {
        this.title.get().subList(2, this.title.get().size()).clear();
        new Text(this.title, Integer.toString(Data.PLAYER_POINTS.get())).setPosition(0.95f, 0.8f).setForeground(Resources.PAINT_M_Y_0050_R);

        panels.get().subList(1, panels.get().size()).clear();

        ((Text) this.panels.get().get(0)).setForeground(Data.PLAYER_HEALTH.get() < 10 && Data.PLAYER_POINTS.get() > 0 ? Resources.PAINT_M_Y_0030_C : (Data.PLAYER_HEALTH.get() == 10 ? Resources.PAINT_M_GRAY_0030_C : Resources.PAINT_M_W_0030_C));
        ((Text) this.skills[0].get().get(0)).setForeground(Data.UPGRADE_SKILL_SHOCK.get() < 1 && Data.PLAYER_POINTS.get() > 0 ? Resources.PAINT_M_Y_0030_C : (Data.UPGRADE_SKILL_SHOCK.get() == 1 ? Resources.PAINT_M_GRAY_0030_C : Resources.PAINT_M_W_0030_C));
        ((Text) this.skills[1].get().get(0)).setForeground(Data.UPGRADE_SKILL_SHIELD.get() < 1 && Data.PLAYER_POINTS.get() > 0 ? Resources.PAINT_M_Y_0030_C : (Data.UPGRADE_SKILL_SHIELD.get() == 1 ? Resources.PAINT_M_GRAY_0030_C : Resources.PAINT_M_W_0030_C));
        ((Text) this.skills[2].get().get(0)).setForeground(Data.UPGRADE_SKILL_XPBOOST.get() < 1 && Data.PLAYER_POINTS.get() > 0 ? Resources.PAINT_M_Y_0030_C : (Data.UPGRADE_SKILL_XPBOOST.get() == 1 ? Resources.PAINT_M_GRAY_0030_C : Resources.PAINT_M_W_0030_C));
        ((Text) this.skills[3].get().get(0)).setForeground(Data.UPGRADE_ENERGY_MULT.get() < 1 && Data.PLAYER_POINTS.get() > 0 ? Resources.PAINT_M_Y_0030_C : (Data.UPGRADE_ENERGY_MULT.get() == 1 ? Resources.PAINT_M_GRAY_0030_C : Resources.PAINT_M_W_0030_C));

        for (int i = 0; i < 4; i++) {
            this.stars[i].setForeground(Data.UPGRADE_RANDOM.get() >= i + 2 ? Resources.PAINT_M_GRAY_0030_C : (Data.UPGRADE_RANDOM.get() >= i + 1 && Data.PLAYER_POINTS.get() > 0 ? Resources.PAINT_M_Y_0030_C : Resources.PAINT_M_W_0030_C));
        }

        for (int i = 0; i < Data.PLAYER_HEALTH.get(); i++) {
            new Image(this.panels, Resources.ICONS[0]).setPosition(0.25f + 0.075f * i, 0.5f).center(true);
        }
    }

    @Override
    public void draw(Canvas c) {
        for (int i = 0; i < Resources.BACKGROUND.length; i++) {
            c.drawBitmap(Resources.BACKGROUND[i], - 50, 0, null);
        }

        this.view.show(c);
    }

}

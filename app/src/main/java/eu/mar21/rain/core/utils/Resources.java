package eu.mar21.rain.core.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import eu.mar21.rain.core.R;

public class Resources {

    public static Bitmap BACKGROUND[];

    public static Bitmap LOGO;

    public static Paint FONT;
    public static Paint ALPHA_ONLY;

    public static double SCREEN_WIDTH;
    public static double SCREEN_HEIGHT;

    public static boolean DONE = false;

    public static Bitmap PLAYER[] = new Bitmap[9];
    public static Bitmap ACID[] = new Bitmap[4];
    public static Bitmap ICONS[] = new Bitmap[3];
    public static Bitmap BARS[] = new Bitmap[6];
    public static Bitmap SKILLF, SKILL;

    public static Bitmap STAR, ENERGY, SHIELD;

    private static Rect RECTANGLE;

    public static Rect RECTANGLE(double x, double y, double w, double h) {
        RECTANGLE.set((int) x, (int) y, (int) (x + w), (int) (y + h));
        return RECTANGLE;
    }

    public static void preload(android.content.res.Resources c) {
        LOGO = BitmapFactory.decodeResource(c, R.drawable.logo);
    }

    public static void loadAll(android.content.res.Resources c, WindowManager wm) {
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getRealMetrics(dm);

        SCREEN_HEIGHT = dm.heightPixels;
        SCREEN_WIDTH = dm.widthPixels;

        BACKGROUND = new Bitmap[8];
        for (int i = 0; i < 8; i++) {
            Bitmap temp = BitmapFactory.decodeResource(c, R.drawable.b0 + i);
            BACKGROUND[i] = Bitmap.createScaledBitmap(temp, (int) (dm.widthPixels * 1.1), dm.heightPixels, true);
        }

        FONT = new Paint();
        FONT.setTextSize(30.0f);
        FONT.setColor(Color.WHITE);

        for (int i = 0; i < 9; i++) {
            PLAYER[i] = BitmapFactory.decodeResource(c, R.drawable.player0 + i);
        }

        for (int i = 0; i < 4; i++) {
            ACID[i] = BitmapFactory.decodeResource(c, R.drawable.acid0 + i);
        }

        SKILLF = BitmapFactory.decodeResource(c, R.drawable.frame);
        SKILL = BitmapFactory.decodeResource(c, R.drawable.ability);

        STAR = BitmapFactory.decodeResource(c, R.drawable.star);
        SHIELD = BitmapFactory.decodeResource(c, R.drawable.armor);
        ENERGY = BitmapFactory.decodeResource(c, R.drawable.ienergy);

        ICONS[0] = BitmapFactory.decodeResource(c, R.drawable.health);
        ICONS[1] = BitmapFactory.decodeResource(c, R.drawable.experience);
        ICONS[2] = BitmapFactory.decodeResource(c, R.drawable.energy);

        BARS[0] = BitmapFactory.decodeResource(c, R.drawable.framebar);
        BARS[1] = BitmapFactory.decodeResource(c, R.drawable.healthbar);
        BARS[2] = BitmapFactory.decodeResource(c, R.drawable.experiencebar);
        BARS[3] = BitmapFactory.decodeResource(c, R.drawable.armorbar);
        BARS[4] = BitmapFactory.decodeResource(c, R.drawable.energybar0);
        BARS[5] = BitmapFactory.decodeResource(c, R.drawable.energybar1);

        for (int i = 0; i < BARS.length; i++) {
            BARS[i] = Bitmap.createScaledBitmap(BARS[i], (int) (BARS[i].getWidth() * 0.9), (int) (BARS[i].getHeight() * 0.9), true);
        }

        for (int i = 0; i < ICONS.length; i++) {
            ICONS[i] = Bitmap.createScaledBitmap(ICONS[i], (int) (ICONS[i].getWidth() * 0.9), (int) (ICONS[i].getHeight() * 0.9), true);
        }


        ALPHA_ONLY = new Paint();

        RECTANGLE = new Rect();

        DONE = true;
    }
}

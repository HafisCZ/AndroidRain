package eu.mar21.rain.core.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import eu.mar21.rain.core.R;

public class Resources {

    public static Paint PAINT_DEFAULT_BG = new Paint();
    static {
        PAINT_DEFAULT_BG.setColor(0x0FFFFFFF);
    }

    public static final Paint FONT_DEBUG = new Paint();
    static {
        FONT_DEBUG.setColor(0xFFFFFFFF);
        FONT_DEBUG.setTypeface(Typeface.MONOSPACE);
        FONT_DEBUG.setTextSize(20);
    }

    public static Bitmap BACKGROUND[];

    public static Bitmap LOGO;

    public static Paint FONT;
    public static Paint ALPHA_ONLY = new Paint();

    public static double SCREEN_WIDTH;
    public static double SCREEN_HEIGHT;

    public static boolean DONE = false;

    public static Bitmap PLAYER[] = new Bitmap[9];
    public static Bitmap ACID[] = new Bitmap[4];
    public static Bitmap ICONS[] = new Bitmap[3];
    public static Bitmap BARS[] = new Bitmap[6];
    public static Bitmap SKILLF, SKILL, SKILLS[] = new Bitmap[3];

    public static Bitmap STAR, ENERGY, SHIELD;

    public static float RES_MULTX = 1.0f, RES_MULTY = 1.0f;

    public static void preload(android.content.res.Resources c) {
        LOGO = BitmapFactory.decodeResource(c, R.drawable.logo);
    }

    public static void loadAll(android.content.res.Resources c, WindowManager wm) {
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getRealMetrics(dm);

        SCREEN_HEIGHT = dm.heightPixels;
        SCREEN_WIDTH = dm.widthPixels;

        RES_MULTX = (float) SCREEN_WIDTH / 1280.0f;
        RES_MULTY = (float) SCREEN_HEIGHT / 720.0f;

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
        for (int i = 0; i < 3; i++) {
            int size = SKILL.getHeight();
            SKILLS[i] = Bitmap.createBitmap(SKILL, i * size, 0, size, size);
        }

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

        DONE = true;
    }
}

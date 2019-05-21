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

    public static Paint PAINT = new Paint();
    public static Paint PAINT_ALPHA = new Paint();
    public static Paint PAINT_DEBUG = new Paint();
    public static Paint PAINT_M_W_0020_L = new Paint();
    public static Paint PAINT_M_W_0020_R = new Paint();
    public static Paint PAINT_M_W_0050_C = new Paint();
    public static Paint PAINT_M_W_0100_C = new Paint();
    public static Paint PAINT_M_W_0020_C = new Paint();
    public static Paint PAINT_M_W_0050_L = new Paint();
    public static Paint PAINT_M_W_0030_L = new Paint();
    public static Paint PAINT_M_LGRAY_0050_L = new Paint();
    public static Paint PAINT_0F8FBC8F = new Paint();
    public static Paint PAINT_2F8FBC8F = new Paint();
    public static Paint PAINT_2FFFBC8F = new Paint();
    public static Paint PAINT_0 = new Paint();
    public static Paint PAINT_M_Y_0050_R = new Paint();
    public static Paint PAINT_M_Y_0030_C = new Paint();
    public static Paint PAINT_M_GRAY_0030_C = new Paint();
    public static Paint PAINT_M_W_0030_C = new Paint();

    public static Bitmap[] BACKGROUND = new Bitmap[8];
    public static Bitmap[] PLAYER = new Bitmap[9];
    public static Bitmap[] ACID = new Bitmap[4];
    public static Bitmap[] ICONS = new Bitmap[3];
    public static Bitmap[] BARS = new Bitmap[6];
    public static Bitmap[] SKILLS = new Bitmap[3];

    public static Bitmap LOGO;
    public static Bitmap SKILLF;
    public static Bitmap SKILL;
    public static Bitmap STAR;
    public static Bitmap ENERGY;
    public static Bitmap SHIELD;

    public static double SCREEN_WIDTH;
    public static double SCREEN_HEIGHT;
    public static float RES_MULTX = 1.0f;
    public static float RES_MULTY = 1.0f;

    public static void loadCore(android.content.res.Resources r, WindowManager wm) {
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getRealMetrics(dm);

        SCREEN_HEIGHT = dm.heightPixels;
        SCREEN_WIDTH = dm.widthPixels;

        RES_MULTX = (float) SCREEN_WIDTH / 1280.0f;
        RES_MULTY = (float) SCREEN_HEIGHT / 720.0f;

        LOGO = BitmapFactory.decodeResource(r, R.drawable.logo);

        PAINT_DEBUG.setColor(0xFFFFFFFF);
        PAINT_DEBUG.setTypeface(Typeface.MONOSPACE);
        PAINT_DEBUG.setTextSize(20);
    }

    public static void loadExtras(android.content.res.Resources r) {
        for (int i = 0; i < 8; i++) {
            Bitmap temp = BitmapFactory.decodeResource(r, R.drawable.b0 + i);
            BACKGROUND[i] = Bitmap.createScaledBitmap(temp, (int) (SCREEN_WIDTH * 1.1), (int) SCREEN_HEIGHT, true);
        }

        for (int i = 0; i < 9; i++) {
            PLAYER[i] = BitmapFactory.decodeResource(r, R.drawable.player0 + i);
        }

        for (int i = 0; i < 4; i++) {
            ACID[i] = BitmapFactory.decodeResource(r, R.drawable.acid0 + i);
        }

        SKILL = BitmapFactory.decodeResource(r, R.drawable.ability);
        for (int i = 0; i < 3; i++) {
            int size = SKILL.getHeight();
            SKILLS[i] = Bitmap.createBitmap(SKILL, i * size, 0, size, size);
        }

        SKILLF = BitmapFactory.decodeResource(r, R.drawable.frame);
        STAR = BitmapFactory.decodeResource(r, R.drawable.star);
        SHIELD = BitmapFactory.decodeResource(r, R.drawable.armor);
        ENERGY = BitmapFactory.decodeResource(r, R.drawable.ienergy);
        ICONS[0] = BitmapFactory.decodeResource(r, R.drawable.health);
        ICONS[1] = BitmapFactory.decodeResource(r, R.drawable.experience);
        ICONS[2] = BitmapFactory.decodeResource(r, R.drawable.energy);
        BARS[0] = BitmapFactory.decodeResource(r, R.drawable.framebar);
        BARS[1] = BitmapFactory.decodeResource(r, R.drawable.healthbar);
        BARS[2] = BitmapFactory.decodeResource(r, R.drawable.experiencebar);
        BARS[3] = BitmapFactory.decodeResource(r, R.drawable.armorbar);
        BARS[4] = BitmapFactory.decodeResource(r, R.drawable.energybar0);
        BARS[5] = BitmapFactory.decodeResource(r, R.drawable.energybar1);

        for (int i = 0; i < BARS.length; i++) {
            BARS[i] = Bitmap.createScaledBitmap(BARS[i], (int) (BARS[i].getWidth() * 0.9), (int) (BARS[i].getHeight() * 0.9), true);
        }

        for (int i = 0; i < ICONS.length; i++) {
            ICONS[i] = Bitmap.createScaledBitmap(ICONS[i], (int) (ICONS[i].getWidth() * 0.9), (int) (ICONS[i].getHeight() * 0.9), true);
        }

        PAINT_M_W_0020_L.setTypeface(Typeface.MONOSPACE);
        PAINT_M_W_0020_L.setColor(Color.WHITE);
        PAINT_M_W_0020_L.setTextAlign(Paint.Align.LEFT);
        PAINT_M_W_0020_L.setTextSize(0.02f * (float) SCREEN_WIDTH);

        PAINT_M_W_0020_R.setTypeface(Typeface.MONOSPACE);
        PAINT_M_W_0020_R.setColor(Color.WHITE);
        PAINT_M_W_0020_R.setTextAlign(Paint.Align.RIGHT);
        PAINT_M_W_0020_R.setTextSize(0.02f * (float) SCREEN_WIDTH);

        PAINT_M_W_0020_C.setTypeface(Typeface.MONOSPACE);
        PAINT_M_W_0020_C.setColor(Color.WHITE);
        PAINT_M_W_0020_C.setTextAlign(Paint.Align.CENTER);
        PAINT_M_W_0020_C.setTextSize(0.02f * (float) SCREEN_WIDTH);

        PAINT_0F8FBC8F.setColor(0x0F8FBC8F);
        PAINT_2F8FBC8F.setColor(0x2F8FBC8F);
        PAINT_2FFFBC8F.setColor(0x2FFFBC8F);

        PAINT_M_W_0050_C.setTypeface(Typeface.MONOSPACE);
        PAINT_M_W_0050_C.setColor(Color.WHITE);
        PAINT_M_W_0050_C.setTextAlign(Paint.Align.CENTER);
        PAINT_M_W_0050_C.setTextSize(0.05f * (float) SCREEN_WIDTH);

        PAINT_M_W_0100_C.setTypeface(Typeface.MONOSPACE);
        PAINT_M_W_0100_C.setColor(Color.WHITE);
        PAINT_M_W_0100_C.setTextAlign(Paint.Align.CENTER);
        PAINT_M_W_0100_C.setTextSize(0.05f * (float) SCREEN_WIDTH);

        PAINT_M_W_0050_L.setTypeface(Typeface.MONOSPACE);
        PAINT_M_W_0050_L.setColor(Color.WHITE);
        PAINT_M_W_0050_L.setTextAlign(Paint.Align.LEFT);
        PAINT_M_W_0050_L.setTextSize(0.05f * (float) SCREEN_WIDTH);

        PAINT_M_LGRAY_0050_L.setTypeface(Typeface.MONOSPACE);
        PAINT_M_LGRAY_0050_L.setColor(Color.LTGRAY);
        PAINT_M_LGRAY_0050_L.setTextAlign(Paint.Align.LEFT);
        PAINT_M_LGRAY_0050_L.setTextSize(0.05f * (float) SCREEN_WIDTH);

        PAINT_M_Y_0050_R.setTypeface(Typeface.MONOSPACE);
        PAINT_M_Y_0050_R.setColor(Color.YELLOW);
        PAINT_M_Y_0050_R.setTextAlign(Paint.Align.LEFT);
        PAINT_M_Y_0050_R.setTextSize(0.05f * (float) SCREEN_WIDTH);

        PAINT_M_Y_0030_C.setTypeface(Typeface.MONOSPACE);
        PAINT_M_Y_0030_C.setColor(Color.YELLOW);
        PAINT_M_Y_0030_C.setTextAlign(Paint.Align.CENTER);
        PAINT_M_Y_0030_C.setTextSize(0.03f * (float) SCREEN_WIDTH);

        PAINT_M_GRAY_0030_C.setTypeface(Typeface.MONOSPACE);
        PAINT_M_GRAY_0030_C.setColor(Color.GRAY);
        PAINT_M_GRAY_0030_C.setTextAlign(Paint.Align.CENTER);
        PAINT_M_GRAY_0030_C.setTextSize(0.03f * (float) SCREEN_WIDTH);

        PAINT_M_W_0030_C.setTypeface(Typeface.MONOSPACE);
        PAINT_M_W_0030_C.setColor(Color.WHITE);
        PAINT_M_W_0030_C.setTextAlign(Paint.Align.CENTER);
        PAINT_M_W_0030_C.setTextSize(0.03f * (float) SCREEN_WIDTH);

        PAINT_M_W_0030_L.setTypeface(Typeface.MONOSPACE);
        PAINT_M_W_0030_L.setColor(Color.WHITE);
        PAINT_M_W_0030_L.setTextAlign(Paint.Align.LEFT);
        PAINT_M_W_0030_L.setTextSize(0.03f * (float) SCREEN_WIDTH);

        PAINT_0.setColor(0x00000000);
    }
}

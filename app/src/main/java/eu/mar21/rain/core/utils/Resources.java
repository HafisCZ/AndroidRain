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
    public static Paint PAINT_LIGHTNING = new Paint();
    public static Paint PAINT_ANNOUNCEMENT = new Paint();
    public static Paint PAINT_ANNOUNCEMENT_2 = new Paint();
    public static Paint PAINT_NOTIFICATION = new Paint();
    public static Paint PAINT_DEBUG = new Paint();
    public static Paint PAINT_M_W_0020_L = new Paint();
    public static Paint PAINT_M_LGRAY_0015_L = new Paint();
    public static Paint PAINT_M_W_0020_R = new Paint();
    public static Paint PAINT_M_W_0050_C = new Paint();
    public static Paint PAINT_M_W_0100_C = new Paint();
    public static Paint PAINT_M_W_0020_C = new Paint();
    public static Paint PAINT_M_W_0050_L = new Paint();
    public static Paint PAINT_M_W_0030_L = new Paint();
    public static Paint PAINT_M_LGRAY_0050_L = new Paint();
    public static Paint PAINT_0F8FBC8F = new Paint();
    public static Paint PAINT_2F8FBC8F = new Paint();
    public static Paint PAINT_108FBC8F = new Paint();
    public static Paint PAINT_2FFFBC8F = new Paint();
    public static Paint PAINT_FF6495ED = new Paint();
    public static Paint PAINT_FF7CFC00 = new Paint();
    public static Paint PAINT_FFADFF2F = new Paint();
    public static Paint PAINT_FFADD8E6 = new Paint();
    public static Paint PAINT_0FF4F442 = new Paint();
    public static Paint PAINT_0E9BF442 = new Paint();
    public static Paint PAINT_0 = new Paint();
    public static Paint PAINT_M_Y_0050_R = new Paint();
    public static Paint PAINT_M_Y_0030_C = new Paint();
    public static Paint PAINT_M_GRAY_0030_C = new Paint();
    public static Paint PAINT_M_W_0030_C = new Paint();
    public static Paint PAINT_0_STROKE = new Paint();
    public static Paint PAINT_FFF0F8FF_STROKE = new Paint();
    public static Paint PAINT_A000FF00_STROKE = new Paint();
    public static Paint PAINT_0E9BF442_STROKE = new Paint();
    public static Paint PAINT_A0FFFF00_STROKE = new Paint();
    public static Paint PAINT_M_A4FFFFFF_0030 = new Paint();
    public static Paint PAINT_M_A4FFFFFF_0020 = new Paint();
    public static Paint PAINT_M_Y_20_C = new Paint();

    public static Bitmap[] BATTERY = new Bitmap[12];

    public static Bitmap[] BACKGROUND = new Bitmap[7];
    public static Bitmap[] PLAYER = new Bitmap[9];
    public static Bitmap[] ACID = new Bitmap[4];
    public static Bitmap[] ICONS = new Bitmap[3];
    public static Bitmap[] BARS = new Bitmap[4];
    public static Bitmap[] SKILLS = new Bitmap[3];

    public static Bitmap LOGO;
    public static Bitmap SKILLF;
    public static Bitmap SKILL;
    public static Bitmap STAR;
    public static Bitmap ENERGY;
    public static Bitmap SHIELD;
    public static Bitmap POLE;

    public static double WIDTH;
    public static double HEIGHT;
    public static double MX = 1.0;
    public static double MY = 1.0;

    // Methods
    public static void loadCore(android.content.res.Resources r, WindowManager wm) {
        Logger.log("Loading core resources ...");

        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getRealMetrics(dm);

        HEIGHT = dm.heightPixels;
        WIDTH = dm.widthPixels;

        MX = WIDTH / 1280.0;
        MY = HEIGHT / 720.0;

        LOGO = BitmapFactory.decodeResource(r, R.drawable.logo);

        PAINT_DEBUG.setColor(0x7DFFFFFF);
        PAINT_DEBUG.setTypeface(Typeface.MONOSPACE);
        PAINT_DEBUG.setTextSize(20);
    }

    public static void loadExtras(android.content.res.Resources r) {
        Logger.log("Loading backgrounds ...");

        for (int i = 0; i < 7; i++) {
            Bitmap temp = BitmapFactory.decodeResource(r, R.drawable.b1 + i);
            BACKGROUND[i] = Bitmap.createScaledBitmap(temp, (int) (WIDTH * 1.1), (int) HEIGHT, true);
        }

        Logger.log("Loading sprites ...");

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
        POLE = BitmapFactory.decodeResource(r, R.drawable.pole);

        for (int i = 0; i < 12; i++) {
            BATTERY[i] = BitmapFactory.decodeResource(r, R.drawable.battery_a + i);
        }

        Logger.log("Extracting sprites ...");

        for (int i = 0; i < BARS.length; i++) {
            BARS[i] = Bitmap.createScaledBitmap(BARS[i], (int) (BARS[i].getWidth() * 0.9), (int) (BARS[i].getHeight() * 0.9), true);
        }

        for (int i = 0; i < ICONS.length; i++) {
            ICONS[i] = Bitmap.createScaledBitmap(ICONS[i], (int) (ICONS[i].getWidth() * 0.9), (int) (ICONS[i].getHeight() * 0.9), true);
        }

        Logger.log("Preparing paints ...");

        PAINT_M_W_0020_L.setTypeface(Typeface.MONOSPACE);
        PAINT_M_W_0020_L.setColor(Color.WHITE);
        PAINT_M_W_0020_L.setTextAlign(Paint.Align.LEFT);
        PAINT_M_W_0020_L.setTextSize(0.02f * (float) WIDTH);

        PAINT_M_W_0020_R.set(PAINT_M_W_0020_L);
        PAINT_M_W_0020_R.setTextAlign(Paint.Align.RIGHT);

        PAINT_M_W_0020_C.set(PAINT_M_W_0020_L);
        PAINT_M_W_0020_C.setTextAlign(Paint.Align.CENTER);

        PAINT_M_W_0050_C.set(PAINT_M_W_0020_C);
        PAINT_M_W_0050_C.setTextSize(0.05f * (float) WIDTH);

        PAINT_ANNOUNCEMENT.set(PAINT_M_W_0050_C);
        PAINT_ANNOUNCEMENT.setTextSize(0.03f * (float) WIDTH);

        PAINT_ANNOUNCEMENT_2.set(PAINT_M_W_0050_C);
        PAINT_ANNOUNCEMENT_2.setTextSize(0.01f * (float) WIDTH);

        PAINT_M_W_0100_C.set(PAINT_M_W_0020_C);
        PAINT_M_W_0100_C.setTextSize(0.1f * (float) WIDTH);

        PAINT_M_W_0050_L.set(PAINT_M_W_0050_C);
        PAINT_M_W_0050_L.setTextAlign(Paint.Align.LEFT);

        PAINT_M_LGRAY_0050_L.set(PAINT_M_W_0050_L);
        PAINT_M_LGRAY_0050_L.setColor(Color.LTGRAY);

        PAINT_M_LGRAY_0015_L.set(PAINT_M_LGRAY_0050_L);
        PAINT_M_LGRAY_0015_L.setTextSize(0.015f * (float) WIDTH);

        PAINT_M_W_0030_C.set(PAINT_M_W_0020_C);
        PAINT_M_W_0030_C.setTextSize(0.03f * (float) WIDTH);

        PAINT_M_W_0030_L.set(PAINT_M_W_0020_L);
        PAINT_M_W_0030_L.setTextSize(0.03f * (float) WIDTH);

        PAINT_M_Y_0050_R.set(PAINT_M_W_0050_C);
        PAINT_M_Y_0050_R.setTextAlign(Paint.Align.RIGHT);
        PAINT_M_Y_0050_R.setColor(Color.YELLOW);

        PAINT_M_Y_0030_C.set(PAINT_M_W_0020_C);
        PAINT_M_Y_0030_C.setTextSize(0.03f * (float) WIDTH);
        PAINT_M_Y_0030_C.setColor(Color.YELLOW);

        PAINT_M_Y_20_C.set(PAINT_M_Y_0030_C);
        PAINT_M_Y_20_C.setTextSize(20);

        PAINT_M_GRAY_0030_C.set(PAINT_M_Y_0030_C);
        PAINT_M_GRAY_0030_C.setColor(Color.GRAY);

        PAINT_0.setColor(0x00000000);
        PAINT_0F8FBC8F.setColor(0x0F8FBC8F);
        PAINT_2F8FBC8F.setColor(0x2F8FBC8F);
        PAINT_2FFFBC8F.setColor(0x2FFFBC8F);
        PAINT_108FBC8F.setColor(0x108FBC8F);
        PAINT_LIGHTNING.setColor(0xFFFFFFFF);
        PAINT_FF6495ED.setColor(0xFF6495ED);
        PAINT_FFADD8E6.setColor(0xFFADD8E6);
        PAINT_FF7CFC00.setColor(0xFF7CFC00);
        PAINT_FFADFF2F.setColor(0xFFADFF2F);
        PAINT_0FF4F442.setColor(0x30F4F442);
        PAINT_0E9BF442.setColor(0x209BF442);

        PAINT_FFF0F8FF_STROKE.setColor(0xFFF0F8FF);
        PAINT_FFF0F8FF_STROKE.setStyle(Paint.Style.STROKE);

        PAINT_0_STROKE.setColor(0);
        PAINT_0_STROKE.setStyle(Paint.Style.STROKE);

        PAINT_0E9BF442_STROKE.setColor(0x509BF442);
        PAINT_0E9BF442_STROKE.setStyle(Paint.Style.STROKE);

        PAINT_A0FFFF00_STROKE.setColor(0xA0FFFF00);
        PAINT_A0FFFF00_STROKE.setStyle(Paint.Style.STROKE);

        PAINT_A000FF00_STROKE.setColor(0xA000FF00);
        PAINT_A000FF00_STROKE.setStyle(Paint.Style.STROKE);

        PAINT_M_A4FFFFFF_0030.setColor(0xA4FFFFFF);
        PAINT_M_A4FFFFFF_0030.setTypeface(Typeface.MONOSPACE);
        PAINT_M_A4FFFFFF_0030.setTextSize(30);

        PAINT_M_A4FFFFFF_0020.set(PAINT_M_A4FFFFFF_0030);
        PAINT_M_A4FFFFFF_0020.setTextSize(20);

        PAINT_NOTIFICATION.set(PAINT_M_W_0020_C);
    }
}

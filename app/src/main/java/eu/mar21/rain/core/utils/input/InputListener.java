package eu.mar21.rain.core.utils.input;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.MotionEvent;
import android.view.View;

import eu.mar21.rain.core.device.Preferences;
import eu.mar21.rain.core.utils.Resources;

import static android.view.MotionEvent.ACTION_CANCEL;
import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;
import static android.view.MotionEvent.ACTION_POINTER_DOWN;
import static android.view.MotionEvent.ACTION_POINTER_UP;
import static android.view.MotionEvent.ACTION_UP;

public class InputListener implements View.OnTouchListener, SensorEventListener {

    public enum TouchMode {
        TAP, SWIPE, SENSOR
    }

    private static final int MAX_CURSORS = 5;

    private Integer cursorsB[][] = new Integer[MAX_CURSORS][2];
    private Integer cursorsE[][] = new Integer[MAX_CURSORS][2];

    @SuppressWarnings("all")
    @Override
    public boolean onTouch(View v, MotionEvent e) {
        int index = e.getActionIndex();
        int ptrid = e.getPointerId(index);
        int msact = e.getActionMasked();

        if (ptrid >= MAX_CURSORS) {
            return false;
        }

        switch (msact) {
            case ACTION_DOWN:
            case ACTION_POINTER_DOWN:
            {
                this.cursorsB[ptrid][0] = (int) e.getX(index);
                this.cursorsB[ptrid][1] = (int) e.getY(index);
                break;
            }
            case ACTION_MOVE:
            {
                this.cursorsE[ptrid][0] = (int) e.getX(index);
                this.cursorsE[ptrid][1] = (int) e.getY(index);
                break;
            }
            case ACTION_UP:
            case ACTION_POINTER_UP:
            case ACTION_CANCEL:
            {
                this.cursorsB[ptrid][0] = null;
                this.cursorsB[ptrid][1] = null;
                this.cursorsE[ptrid][0] = null;
                this.cursorsE[ptrid][1] = null;
            }
        }

        return true;
    }

    private Direction sensorDirection = Direction.NONE;
    private float gravity[];
    private float magnetic[];
    private float accels[] = new float[3];
    private float mags[] = new float[3];
    private float values[] = new float[3];
    private float azimuth;
    private float pitch;
    private float roll;

    @Override
    public void onSensorChanged(SensorEvent e) {
        switch (e.sensor.getType()) {
            case Sensor.TYPE_MAGNETIC_FIELD : {
                this.mags = e.values.clone();
                break;
            }
            case Sensor.TYPE_ACCELEROMETER : {
                this.accels = e.values.clone();
                break;
            }
        }

        if (this.mags != null && this.accels != null) {
            this.gravity = new float[9];
            this.magnetic = new float[9];
            SensorManager.getRotationMatrix(this.gravity, this.magnetic, this.accels, this.mags);

            float[] outGravity = new float[9];
            SensorManager.remapCoordinateSystem(this.gravity, SensorManager.AXIS_X,SensorManager.AXIS_Z, outGravity);
            SensorManager.getOrientation(outGravity, this.values);

            this.azimuth = this.values[0] * 57.2957795f;
            this.pitch = this.values[1] * 57.2957795f;
            this.roll = this.values[2] * 57.2957795f;
            this.mags = null;
            this.accels = null;

            this.sensorDirection = this.roll < -98 ? Direction.LEFT : (this.roll > -82 ? Direction.RIGHT : Direction.NONE);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor s, int a) {

    }

    private static TouchMode mode = TouchMode.values()[Preferences.get("control.mode", 0)];

    public static void setMode(TouchMode m) {
        mode = m;
        Preferences.set("control.mode", mode.ordinal());
    }

    public static TouchMode getMode() {
        return mode;
    }

    public enum ControlZone {

        WHOLE_SCREEN(0, 0, Resources.SCREEN_WIDTH, Resources.SCREEN_HEIGHT),
        LU_QUAD(0, 0, Resources.SCREEN_WIDTH / 3, Resources.SCREEN_HEIGHT / 2),
        LC_QUAD(Resources.SCREEN_WIDTH / 3, 0, Resources.SCREEN_WIDTH / 3, Resources.SCREEN_HEIGHT / 2),
        LD_QUAD(0, Resources.SCREEN_HEIGHT / 2, Resources.SCREEN_WIDTH / 2, Resources.SCREEN_HEIGHT / 2),
        RU_QUAD(2 * Resources.SCREEN_WIDTH / 3, 0, Resources.SCREEN_WIDTH / 3, Resources.SCREEN_HEIGHT / 2),
        RD_QUAD(Resources.SCREEN_WIDTH / 2, Resources.SCREEN_HEIGHT / 2, Resources.SCREEN_WIDTH / 2, Resources.SCREEN_HEIGHT / 2),
        ROW3(0, Resources.SCREEN_HEIGHT / 2, Resources.SCREEN_WIDTH, Resources.SCREEN_HEIGHT / 4),
        ROW4(0, 3 * Resources.SCREEN_HEIGHT / 4, Resources.SCREEN_WIDTH, Resources.SCREEN_HEIGHT / 4),
        UP(0, 0, Resources.SCREEN_WIDTH, Resources.SCREEN_HEIGHT / 2),
        DOWN(0, Resources.SCREEN_HEIGHT / 2, Resources.SCREEN_WIDTH, Resources.SCREEN_HEIGHT / 2);

        private final int x, y, xx, yy;
        private boolean lock;

        ControlZone(double x, double y, double w, double h) {
            this.x = (int) x;
            this.y = (int) y;
            this.xx = this.x + (int) w;
            this.yy = this.y + (int) h;
            this.lock = false;
        }

        public boolean inside(int x, int y) {
            return x > this.x && x < this.xx && y > this.y && y < this.yy;
        }
    }

    private boolean inside(int x, int y, int bx, int by, int ex, int ey) {
        return x > bx && x < ex && y > by && y < ey;
    }

    public boolean isHeld(ControlZone zone) {
        boolean held = false;

        for (int i = 0; i < MAX_CURSORS; i++) {
            if (this.cursorsB[i][1] != null) {
                held |= zone.inside(this.cursorsB[i][0], this.cursorsB[i][1]);
            }
        }

        return held;
    }

    public boolean isPressed(ControlZone zone) {
        final boolean lock = zone.lock;

        zone.lock = isHeld(zone);
        return zone.lock && !lock;
    }

    public boolean isPressed(int bx, int by, int ex, int ey) {
        return isHeld(bx, by, ex, ey);
    }

    public void reset() {
        for (int i = 0; i < MAX_CURSORS; i++) {
            this.cursorsB[i][0] = null;
            this.cursorsB[i][1] = null;
            this.cursorsE[i][0] = null;
            this.cursorsE[i][1] = null;
        }
    }

    public enum Direction {
        NONE,
        RIGHT, LEFT,
        UP, DOWN
    }

    private boolean isHeld(int bx, int by, int ex, int ey) {
        boolean held = false;

        for (int i = 0; i < MAX_CURSORS; i++) {
            if (this.cursorsB[i][1] != null) {
                held |= inside(this.cursorsB[i][0], this.cursorsB[i][1], bx, by, ex, ey);
            }
        }

        return held;
    }

    public Direction isTouch(ControlZone zone) {
        if (mode == TouchMode.TAP) {
            final boolean isRigth = isHeld(zone.x + (zone.xx - zone.x) / 2, zone.y, zone.xx, zone.yy);
            final boolean isLeft = isHeld(zone.x, zone.y, (zone.xx - zone.x) / 2 + zone.x, zone.yy);
            if (isRigth && !isLeft) {
                return Direction.RIGHT;
            }
            else if (isLeft && !isRigth) {
                return Direction.LEFT;
            }
            else {
                return Direction.NONE;
            }
        } else if (mode == TouchMode.SWIPE) {
            return isSwipe(zone);
        } else {
            return sensorDirection;
        }
    }

    private Direction isSwipe(ControlZone zone) {
        Direction direction = Direction.NONE;

        for (int i = 0; i < MAX_CURSORS; i++) {
            if (this.cursorsB[i][0] != null && this.cursorsE[i][0] != null) {
                if (zone.inside(this.cursorsB[i][0], this.cursorsB[i][1])) {
                    double sx = this.cursorsE[i][0] - this.cursorsB[i][0];
                    double sy = this.cursorsE[i][1] - this.cursorsB[i][1];

                    if (Math.abs(sx) > Math.abs(sy)) {
                        direction = sx > 0 ? Direction.RIGHT : Direction.LEFT;
                    } else {
                        direction = sy > 0 ? Direction.DOWN : Direction.UP;
                    }

                    return direction;
                }
            }
        }

        return direction;
    }

}

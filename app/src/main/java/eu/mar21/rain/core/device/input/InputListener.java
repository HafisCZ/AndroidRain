package eu.mar21.rain.core.device.input;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.MotionEvent;
import android.view.View;

import eu.mar21.rain.core.device.Preferences;
import eu.mar21.rain.core.utils.Direction;

import static android.view.MotionEvent.ACTION_CANCEL;
import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;
import static android.view.MotionEvent.ACTION_POINTER_DOWN;
import static android.view.MotionEvent.ACTION_POINTER_UP;
import static android.view.MotionEvent.ACTION_UP;

public class InputListener implements View.OnTouchListener, SensorEventListener {

    // Defaults
    private static final int CURSOR_COUNT_MAX = 5;

    // Params
    private Double[][] cursorDW;
    private Double[][] cursorUP;
    private float[] mags = null;
    private float[] accl = null;
    private boolean[] locks;
    private Direction sdir;

    public static InputStyle STYLE = InputStyle.values()[Preferences.get("control.style", 0)];

    // Constructor
    public InputListener() {
        this.sdir = Direction.NONE;

        this.mags = null;
        this.accl = null;

        this.cursorDW = new Double[CURSOR_COUNT_MAX][2];
        this.cursorUP = new Double[CURSOR_COUNT_MAX][2];

        this.locks = new boolean[TouchZone.values().length];
    }

    // Functions
    public static void setDirectionSource(InputStyle style) {
        STYLE = style;
        Preferences.set("control.style", style.ordinal());
    }

    public static InputStyle getDirectionSource() {
        return STYLE;
    }

    @Override
    public boolean onTouch(View v, MotionEvent e) {
        int index = e.getActionIndex();
        int ptrid = e.getPointerId(index);

        if (ptrid < CURSOR_COUNT_MAX) {
            switch (e.getActionMasked()) {
                case ACTION_DOWN:
                case ACTION_POINTER_DOWN:
                {
                    this.cursorDW[ptrid][0] = (double) e.getX(index);
                    this.cursorDW[ptrid][1] = (double) e.getY(index);
                    break;
                }
                case ACTION_MOVE:
                {
                    this.cursorUP[ptrid][0] = (double) e.getX(index);
                    this.cursorUP[ptrid][1] = (double) e.getY(index);
                    break;
                }
                case ACTION_UP:
                case ACTION_POINTER_UP:
                case ACTION_CANCEL:
                {
                    this.cursorDW[ptrid][0] = null;
                    this.cursorDW[ptrid][1] = null;
                    this.cursorUP[ptrid][0] = null;
                    this.cursorUP[ptrid][1] = null;
                }
            }

            return true;
        }

        return false;
    }

    @Override
    public void onSensorChanged(SensorEvent e) {
        if (e.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) this.mags = e.values.clone();
        else if (e.sensor.getType() == Sensor.TYPE_ACCELEROMETER) this.accl = e.values.clone();

        if (this.mags != null && this.accl != null) {
            float[] grav = new float[9];
            float[] mags = new float[9];
            float[] ogrv = new float[9];
            float[] vals = new float[3];

            SensorManager.getRotationMatrix(grav, mags, this.accl, this.mags);
            SensorManager.remapCoordinateSystem(grav, SensorManager.AXIS_X, SensorManager.AXIS_Z, ogrv);
            SensorManager.getOrientation(ogrv, vals);

            float roll = vals[2] * 57.2957795f;

            this.mags = null;
            this.accl = null;

            this.sdir = roll < -98 ? Direction.LEFT : (roll > -82 ? Direction.RIGHT : Direction.NONE);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor s, int a) {

    }

    public boolean isDown(TouchZone zone) {
        for (Double[] cursor : this.cursorDW) {
            if (cursor[1] != null && zone.inside(cursor[0], cursor[1])) {
                return true;
            }
        }

        return false;
    }

    public boolean isPressed(TouchZone zone) {
        boolean lock = this.locks[zone.ordinal()];
        this.locks[zone.ordinal()] = isDown(zone);
        return !lock && this.locks[zone.ordinal()];
    }

    public void reset() {
        for (int i = 0; i < CURSOR_COUNT_MAX; i++) {
            this.cursorDW[i] = new Double[] { null, null };
            this.cursorUP[i] = new Double[] { null, null };
        }
    }

    public boolean isHeld(double bx, double by, double ex, double ey) {
        for (Double[] cursor : this.cursorDW) {
            if (cursor[1] != null && cursor[0] > bx && cursor[0] < ex && cursor[1] > by && cursor[1] < ey) {
                return true;
            }
        }

        return false;
    }

    public Direction getDirection(TouchZone zone) {
        if (STYLE == InputStyle.TAP) {
            boolean r = isHeld(zone.x + zone.w / 2.0, zone.y, zone.x + zone.w, zone.y + zone.h);
            boolean l = isHeld(zone.x, zone.y, zone.x + zone.w / 2.0, zone.y + zone.h);

            if (r && !l) return Direction.RIGHT;
            if (l && !r) return Direction.LEFT;
            return Direction.NONE;
        } else if (STYLE == InputStyle.SWIPE) {
            for (int i = 0; i < CURSOR_COUNT_MAX; i++) {
                if (this.cursorDW[i][0] != null && this.cursorUP[i][0] != null && zone.inside(this.cursorDW[i][0], this.cursorDW[i][1])) {
                    double sx = this.cursorUP[i][0] - this.cursorDW[i][0];
                    double sy = this.cursorUP[i][1] - this.cursorDW[i][1];

                    if (Math.abs(sx) > Math.abs(sy)) {
                        return sx > 0 ? Direction.RIGHT : Direction.LEFT;
                    } else {
                        return sy > 0 ? Direction.DOWN : Direction.UP;
                    }
                }
            }

            return Direction.NONE;
        } else {
            return this.sdir;
        }
    }

}

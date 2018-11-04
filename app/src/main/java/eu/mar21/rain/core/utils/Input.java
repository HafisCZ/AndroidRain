package eu.mar21.rain.core.utils;

public class Input {

    public static Vec3d getSwipeDirection(double vx, double vy, float correction) {
        final double ax = Math.abs(vx);
        final double ay = Math.abs(vy);

        if (ax < correction && ay < correction) {
            return new Vec3d(0, 0);
        } else {
            if (Math.abs(vx) > Math.abs(vy)) {
                return new Vec3d(vx > 0 ? 1 : -1, 0);
            } else {
                return new Vec3d(0, vy > 0 ? 1 : -1);
            }
        }
    }

}

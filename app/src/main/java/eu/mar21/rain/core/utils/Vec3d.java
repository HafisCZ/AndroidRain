package eu.mar21.rain.core.utils;

public class Vec3d {

    public double x, y;

    public Vec3d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vec3d(Vec3d v) {
        this.x = v.x;
        this.y = v.y;
    }

    public double length() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public Vec3d divide(double value) {
        return new Vec3d(this.x / value, this.y / value);
    }

    public Vec3d add(Vec3d value) {
        return new Vec3d(this.x + value.x, this.y + value.y);
    }

    public double getAngle() {
        if (this.x  == 0) {
            return 0;
        } else {
            return Math.atan(this.y / this.x);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Vec3d) {
            return this.x == ((Vec3d) o).x && this.y == ((Vec3d) o).y;
        } else {
            return false;
        }
    }

    public Vec3d normalize() {
        double length = length();

        if (length > 0) {
            return divide(length);
        } else {
            return new Vec3d(this);
        }
    }

}

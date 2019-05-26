package eu.mar21.rain.core.utils;

import java.util.Random;

public class Number {

    // Params
    private static final Random RANDOM = new Random();

    // Methods
    public static float between(float min, float max) {
        return min + RANDOM.nextFloat() * (max - min);
    }

    public static double between(double min, double max) {
        return min + RANDOM.nextDouble() * (max - min);
    }

    public static int between(int min, int max) {
        return min + RANDOM.nextInt(max - min);
    }

    public static boolean bool() {
        return RANDOM.nextBoolean();
    }

}

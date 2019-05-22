package eu.mar21.rain.core.utils;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

public class Logger {

    // Params
    private static final List<String> messages = new ArrayList<>();

    // Methods
    public static void log(String message) {
        messages.add(message);
    }

    public static void clear() {
        messages.clear();
    }

    public static void draw(Canvas c) {
        for (int i = 0; i < Math.min(20, messages.size()); i++) {
            c.drawText(messages.get(i + (messages.size() > 20 ? messages.size() - 20 : 0)), 10, 30 + i * 30, Resources.PAINT_DEBUG);
        }
    }
}

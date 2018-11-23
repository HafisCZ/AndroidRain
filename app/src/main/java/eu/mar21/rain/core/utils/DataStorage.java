package eu.mar21.rain.core.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class DataStorage {

    public static DataStorage INSTANCE;

    private static String KEY_PREFIX = "eu.mar21.rain.";

    public static void init(Activity activity) {
        INSTANCE = new DataStorage(activity.getSharedPreferences("eu.mar21.rain", Context.MODE_PRIVATE));
    }

    private final SharedPreferences preferences;

    private DataStorage(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public int get(String key, int def) {
        return this.preferences.getInt(KEY_PREFIX + key, def);
    }

    public void set(String key, int value) {
        this.preferences.edit().putInt(KEY_PREFIX + key, value).apply();
    }

}

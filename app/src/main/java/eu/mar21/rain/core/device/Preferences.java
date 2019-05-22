package eu.mar21.rain.core.device;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import eu.mar21.rain.core.utils.Logger;

public class Preferences {

    // Params
    private static SharedPreferences PREFERENCES = null;
    private static String PREFIX = null;

    // Methods
    public static void init(Activity activity, String prefix) {
        Logger.log("Loading preferences ...");

        PREFERENCES = activity.getSharedPreferences(prefix, Context.MODE_PRIVATE);
        PREFIX = prefix;
    }

    public static int get(String key, int def) {
        return PREFERENCES.getInt(PREFIX + key, def);
    }

    public static void set(String key, int val) {
        PREFERENCES.edit().putInt(PREFIX + key, val).apply();
    }


}

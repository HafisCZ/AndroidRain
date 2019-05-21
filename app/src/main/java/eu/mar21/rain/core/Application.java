package eu.mar21.rain.core;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import eu.mar21.rain.core.device.Preferences;
import eu.mar21.rain.core.graphics.Window;
import eu.mar21.rain.core.scene.Game;
import eu.mar21.rain.core.scene.Intro;
import eu.mar21.rain.core.scene.menu.Menu;
import eu.mar21.rain.core.scene.menu.Settings;
import eu.mar21.rain.core.scene.menu.Shop;
import eu.mar21.rain.core.scene.menu.Stat;
import eu.mar21.rain.core.utils.Resources;

public class Application extends Activity {

    // Statics
    private static Application INSTANCE;

    // Activity constructor
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        getWindow().getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
            View.SYSTEM_UI_FLAG_FULLSCREEN |
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        INSTANCE = this;

        Resources.loadCore(getResources(), getWindowManager());

        Window window = new Window(this);
        setContentView(window);

        window.registerScene(Intro.class);
        window.requestScene(Intro.class);

        Preferences.init(this, "eu.mar21.rain.");

        new Thread(() -> {
            Resources.loadExtras(getResources());
            window.registerScene(Game.class);
            window.registerScene(Menu.class);
            window.registerScene(Stat.class);
            window.registerScene(Shop.class);
            window.registerScene(Settings.class);

            window.requestScene(Menu.class);
        }).start();
    }

    // Methods
    public static Application get() {
        return INSTANCE;
    }

}

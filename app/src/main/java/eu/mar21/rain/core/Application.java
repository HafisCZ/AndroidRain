package eu.mar21.rain.core;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import eu.mar21.rain.core.graphics.Renderer;
import eu.mar21.rain.core.scene.Game;
import eu.mar21.rain.core.scene.Intro;
import eu.mar21.rain.core.scene.menu.Menu;
import eu.mar21.rain.core.scene.menu.Stat;
import eu.mar21.rain.core.utils.DataStorage;
import eu.mar21.rain.core.utils.Resources;

@SuppressWarnings("unchecked")
public class Application extends Activity {

    private Renderer renderer = null;

    public static Thread LOADER_THREAD = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Resources.preload(getResources());

        this.renderer = new Renderer(this);
        setContentView(this.renderer);

        this.renderer.registerScene(Intro.class);
        this.renderer.requestScene(Intro.class);

        LOADER_THREAD = new Thread(() -> {
            Resources.loadAll(getResources(), getWindowManager());
            DataStorage.init(this);

            this.renderer.registerScene(Game.class);
            this.renderer.registerScene(Menu.class);
            this.renderer.registerScene(Stat.class);
            this.renderer.requestScene(Menu.class);
        });

        LOADER_THREAD.start();
    }
}

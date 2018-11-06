package eu.mar21.rain.core;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import eu.mar21.rain.core.graphics.Renderer;
import eu.mar21.rain.core.scene.Game;
import eu.mar21.rain.core.scene.Intro;
import eu.mar21.rain.core.utils.Resources;

public class Application extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);

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

        Resources.loadAll(getResources(), getWindowManager());

        final Renderer renderer = findViewById(R.id.renderer);

        renderer.setParentActivity(this);

        renderer.registerScene(Intro.class);
        renderer.registerScene(Game.class);

        renderer.sheduleActiveOverlay(Intro.class);
    }
}
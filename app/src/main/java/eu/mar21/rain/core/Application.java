package eu.mar21.rain.core;

import android.app.Activity;
import android.os.Bundle;

import eu.mar21.rain.core.graphics.Renderer;
import eu.mar21.rain.core.scene.Game;
import eu.mar21.rain.core.scene.Intro;
import eu.mar21.rain.core.scene.Pause;
import eu.mar21.rain.core.scene.Victory;
import eu.mar21.rain.core.utils.Resources;

public class Application extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);

        Resources.load(this);

        final Renderer renderer = findViewById(R.id.renderer);

        renderer.setParentActivity(this);

        renderer.registerScene(Game.class);
        renderer.registerScene(Pause.class);
        renderer.registerScene(Victory.class);
    }
}

package eu.mar21.rain.core.error;

import eu.mar21.rain.core.scene.Scene;

public class SceneNotRegisteredException extends Exception {

    public SceneNotRegisteredException(Class<? extends Scene> scene) {
        super(scene.getName() + " not registered!");
    }

}

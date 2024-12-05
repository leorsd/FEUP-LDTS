package Visualizer;

import java.io.IOException;
import GUI.GUI;

public abstract class SceneVisualizer<T> {
    private final T scene;

    public SceneVisualizer(T scene) {
        this.scene = scene;
    }

    public T getScene() {
        return scene;
    }

    public void draw(GUI gui) throws IOException {
        gui.clear();
        drawElements(gui);
        gui.refresh();
    }

    protected abstract void drawElements(GUI gui) throws IOException;
}

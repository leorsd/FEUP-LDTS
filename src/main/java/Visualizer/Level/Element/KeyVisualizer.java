package Visualizer.Level.Element;

import Model.Elements.Key;
import GUI.GUI;

public class KeyVisualizer implements ElementVisualizer<Key> {
    @Override
    public void draw(Key key, GUI gui) {
        gui.drawKey(key);
    }
}

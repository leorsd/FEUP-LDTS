package visualizer.level.element;

import model.elements.Key;
import gui.GUI;

public class KeyVisualizer implements ElementVisualizer<Key> {
    @Override
    public void draw(Key key, GUI gui) {
        gui.drawKey(key);
    }
}

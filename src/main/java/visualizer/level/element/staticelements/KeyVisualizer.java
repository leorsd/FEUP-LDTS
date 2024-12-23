package visualizer.level.element.staticelements;

import model.elements.staticelements.Key;
import gui.GUI;
import visualizer.level.element.ElementVisualizer;

public class KeyVisualizer implements ElementVisualizer<Key> {
    @Override
    public void draw(Key key, GUI gui) {
        if (!key.isCollected()) {
            gui.drawImage(key.getPosition(), key.getImage());
        }
    }
}

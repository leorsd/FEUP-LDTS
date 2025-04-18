package visualizer.level.element.staticelements;

import model.elements.staticelements.Key;
import gui.GUI;

public class KeyVisualizer implements StaticElementVisualizer<Key> {
    @Override
    public void draw(Key key, GUI gui) {
        if (!key.isCollected()) {
            gui.drawImage(key.getPosition(), key.getImage());
        }
    }
}

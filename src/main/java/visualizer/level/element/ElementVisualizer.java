package visualizer.level.element;

import model.elements.Element;
import gui.GUI;

public interface ElementVisualizer<T extends Element> {
    void draw(T element, GUI gui);
}

package visualizer.level.element;

import model.elements.Element;
import gui.GUI;

import java.io.IOException;

public interface ElementVisualizer<T extends Element> {
    void draw(T element, GUI gui) throws IOException;
}

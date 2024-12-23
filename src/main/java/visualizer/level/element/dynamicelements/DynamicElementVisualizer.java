package visualizer.level.element.dynamicelements;

import gui.GUI;
import model.elements.dynamicelements.DynamicElement;
import visualizer.level.element.ElementVisualizer;

import java.io.IOException;

public interface DynamicElementVisualizer<T extends DynamicElement> extends ElementVisualizer<T> {
    @Override
    void draw(T dynamicElement, GUI gui) throws IOException;
}

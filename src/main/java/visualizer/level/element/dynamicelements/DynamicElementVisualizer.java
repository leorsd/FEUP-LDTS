package visualizer.level.element.dynamicelements;

import gui.GUI;
import model.elements.dynamicelements.DynamicElement;
import visualizer.level.element.ElementVisualizer;

public interface DynamicElementVisualizer<T extends DynamicElement> extends ElementVisualizer<T> {
    @Override
    void draw(T character, GUI gui);
}

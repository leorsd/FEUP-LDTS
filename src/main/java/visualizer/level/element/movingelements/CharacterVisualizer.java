package visualizer.level.element.movingelements;

import gui.GUI;
import model.elements.movingelements.MovingElement;
import visualizer.level.element.ElementVisualizer;

public interface CharacterVisualizer<T extends MovingElement> extends ElementVisualizer<T> {
    @Override
    void draw(T character, GUI gui);
}

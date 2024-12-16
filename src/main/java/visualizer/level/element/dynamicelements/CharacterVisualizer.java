package visualizer.level.element.dynamicelements;

import gui.GUI;
import model.elements.movingelements.MovingElement;
import visualizer.level.element.ElementVisualizer;

import java.io.IOException;

public interface CharacterVisualizer<T extends MovingElement> extends ElementVisualizer<T> {
    @Override
    void draw(T character, GUI gui) throws IOException;
}

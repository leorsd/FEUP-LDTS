package visualizer.level.element.staticelements;

import gui.GUI;
import model.elements.staticelements.StaticElement;
import visualizer.level.element.ElementVisualizer;

import java.io.IOException;

public interface StaticElementVisualizer<T extends StaticElement> extends ElementVisualizer<T>  {
    @Override
    void draw(T staticElement, GUI gui) throws IOException;
}

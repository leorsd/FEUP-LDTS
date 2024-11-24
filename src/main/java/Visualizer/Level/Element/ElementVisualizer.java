package Visualizer.Level.Element;

import Model.Elements.Element;
import GUI.GUI;

public interface ElementVisualizer<T extends Element> {
    void draw(T element, GUI gui);
}

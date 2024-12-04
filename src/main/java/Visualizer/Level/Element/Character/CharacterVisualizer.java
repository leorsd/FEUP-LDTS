package Visualizer.Level.Element.Character;

import GUI.GUI;
import Model.Elements.MovingElements.MovingElement;
import Visualizer.Level.Element.ElementVisualizer;

public interface CharacterVisualizer<T extends MovingElement> extends ElementVisualizer<T> {
    @Override
    void draw(T character, GUI gui);
}

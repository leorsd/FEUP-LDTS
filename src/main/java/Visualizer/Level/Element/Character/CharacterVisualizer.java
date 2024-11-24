package Visualizer.Level.Element.Character;

import GUI.GUI;
import Model.Elements.Characters.Character;
import Visualizer.Level.Element.ElementVisualizer;

public interface CharacterVisualizer<T extends Character> extends ElementVisualizer<T> {
    @Override
    void draw(T character, GUI gui);
}

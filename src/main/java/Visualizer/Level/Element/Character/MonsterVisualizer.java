package Visualizer.Level.Element.Character;

import Model.Elements.MovingElements.Monster;
import GUI.GUI;

public class MonsterVisualizer implements CharacterVisualizer<Monster> {
    @Override
    public void draw(Monster monster, GUI gui) {
        gui.drawMonster(monster);
    }
}

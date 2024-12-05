package visualizer.level.element.Character;

import model.elements.movingelements.Monster;
import gui.GUI;

public class MonsterVisualizer implements CharacterVisualizer<Monster> {
    @Override
    public void draw(Monster monster, GUI gui) {
        gui.drawMonster(monster);
    }
}

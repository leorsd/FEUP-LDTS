package visualizer.level.element.dynamicelements;

import model.elements.dynamicelements.Monster;
import gui.GUI;

public class MonsterVisualizer implements DynamicElementVisualizer<Monster> {
    @Override
    public void draw(Monster monster, GUI gui) {
        gui.drawMonster(monster);
    }
}

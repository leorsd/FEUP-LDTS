package visualizer.level.element.staticelements;

import gui.GUI;
import model.elements.staticelements.Wall;

public class WallVisualizer implements StaticElementVisualizer<Wall> {
    @Override
    public void draw(Wall wall, GUI gui) {
        gui.drawImage(wall.getPosition(), wall.getImage());
    }
}

package visualizer.level.element.staticelements;

import gui.GUI;
import model.elements.staticelements.Wall;
import visualizer.level.element.ElementVisualizer;

public class WallVisualizer implements ElementVisualizer<Wall> {
    @Override
    public void draw(Wall wall, GUI gui) {
        gui.drawImage(wall.getPosition(), wall.getImage());
    }
}

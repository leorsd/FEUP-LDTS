package visualizer.level.element;

import gui.GUI;
import model.elements.Wall;

public class WallVisualizer implements ElementVisualizer<Wall> {
    @Override
    public void draw(Wall wall, GUI gui) {
        gui.drawWall(wall);
    }
}

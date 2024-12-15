package visualizer.level.element;

import gui.GUI;
import model.elements.ToggleableWall;
import model.elements.Wall;

public class ToggleableWallVisualizer implements ElementVisualizer<ToggleableWall> {
    @Override
    public void draw(ToggleableWall wall, GUI gui) {
        gui.drawWall(wall);
    }
}

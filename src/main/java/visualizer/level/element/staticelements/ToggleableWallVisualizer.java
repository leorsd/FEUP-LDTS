package visualizer.level.element.staticelements;

import gui.GUI;
import model.elements.staticelements.ToggleableWall;
import visualizer.level.element.ElementVisualizer;

public class ToggleableWallVisualizer implements ElementVisualizer<ToggleableWall> {
    @Override
    public void draw(ToggleableWall wall, GUI gui) {
        if (wall.isActive()) gui.drawWall(wall);
    }
}

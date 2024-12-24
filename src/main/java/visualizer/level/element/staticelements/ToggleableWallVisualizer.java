package visualizer.level.element.staticelements;

import gui.GUI;
import model.elements.staticelements.ToggleableWall;

public class ToggleableWallVisualizer implements StaticElementVisualizer<ToggleableWall> {
    @Override
    public void draw(ToggleableWall wall, GUI gui) {
        if (wall.isActive()) gui.drawImage(wall.getPosition(), wall.getImage());
    }
}

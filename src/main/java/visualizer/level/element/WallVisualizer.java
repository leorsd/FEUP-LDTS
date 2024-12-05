package Visualizer.Level.Element;

import GUI.GUI;
import Model.Elements.Wall;

public class WallVisualizer implements ElementVisualizer<Wall> {
    @Override
    public void draw(Wall wall, GUI gui) {
        gui.drawWall(wall);
    }
}

package visualizer.level;

import model.elements.Element;
import model.Position;
import model.scenes.Level;
import visualizer.level.element.*;
import visualizer.level.element.movingelements.MonsterVisualizer;
import visualizer.level.element.movingelements.PlayerVisualizer;
import visualizer.SceneVisualizer;
import gui.GUI;

import java.awt.image.BufferedImage;
import java.util.List;

public class LevelVisualizer extends SceneVisualizer<Level> {

    public LevelVisualizer(Level level) {
        super(level);
    }

    @Override
    public void drawElements(GUI gui) {
        drawBackGround(gui, getScene().getBackground());
        drawElements(gui, getScene().getTraps(), new TrapVisualizer());
        drawElements(gui, getScene().getWalls(), new WallVisualizer());
        drawElements(gui, getScene().getMonsters(), new MonsterVisualizer());
        drawElements(gui, getScene().getKeys(), new KeyVisualizer());
        drawElement(gui, getScene().getLevelEndingWall(), new WallVisualizer());
        drawElement(gui, getScene().getPlayer1(), new PlayerVisualizer());
        drawElement(gui, getScene().getPlayer2(), new PlayerVisualizer());
    }

    private <T extends Element> void drawElements(GUI gui, List<T> elements, ElementVisualizer<T> viewer) {
        for (T element : elements)
            drawElement(gui, element, viewer);
    }

    private <T extends Element> void drawElement(GUI gui, T element, ElementVisualizer<T> viewer) {
        viewer.draw(element, gui);
    }

    private void drawBackGround(GUI gui, BufferedImage image) {
        gui.drawImage(new Position(0,0),image);
    }
}

package Visualizer.Level;

import Model.Elements.Element;
import Model.Scenes.Level;
import Visualizer.Level.Element.*;
import Visualizer.Level.Element.Character.MonsterVisualizer;
import Visualizer.Level.Element.Character.PlayerVisualizer;
import Visualizer.SceneVisualizer;
import GUI.GUI;

import java.util.List;

public class LevelVisualizer extends SceneVisualizer<Level> {

    public LevelVisualizer(Level level) {
        super(level);
    }

    @Override
    public void drawElements(GUI gui) {
        drawElements(gui, getScene().getTraps(), new TrapVisualizer());
        drawElements(gui, getScene().getWalls(), new WallVisualizer());
        drawElements(gui, getScene().getMonsters(), new MonsterVisualizer());
        drawElements(gui, getScene().getKeys(), new KeyVisualizer());
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
}

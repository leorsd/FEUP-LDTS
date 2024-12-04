package Visualizer.Level.Element.Character;

import GUI.GUI;
import Model.Elements.MovingElements.Player;

public class PlayerVisualizer implements CharacterVisualizer<Player> {
    @Override
    public void draw(Player player, GUI gui) {
        gui.drawPlayer(player);
    }
}

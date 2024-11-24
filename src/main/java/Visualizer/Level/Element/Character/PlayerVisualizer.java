package Visualizer.Level.Element.Character;

import GUI.GUI;
import Model.Elements.Characters.Player;

public class PlayerVisualizer implements CharacterVisualizer<Player> {
    @Override
    public void draw(Player player, GUI gui) {
        gui.drawPlayer(player);
    }
}

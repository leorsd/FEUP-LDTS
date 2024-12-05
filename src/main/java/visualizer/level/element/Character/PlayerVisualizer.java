package visualizer.level.element.Character;

import gui.GUI;
import model.elements.movingelements.Player;

public class PlayerVisualizer implements CharacterVisualizer<Player> {
    @Override
    public void draw(Player player, GUI gui) {
        gui.drawPlayer(player);
    }
}

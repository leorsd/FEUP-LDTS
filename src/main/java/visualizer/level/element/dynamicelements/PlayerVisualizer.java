package visualizer.level.element.dynamicelements;

import gui.GUI;
import model.elements.dynamicelements.Player;

public class PlayerVisualizer implements DynamicElementVisualizer<Player> {
    @Override
    public void draw(Player player, GUI gui) {
        gui.drawPlayer(player);
    }
}

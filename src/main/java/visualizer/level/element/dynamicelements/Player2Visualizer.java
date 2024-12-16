package visualizer.level.element.dynamicelements;

import gui.GUI;
import model.elements.dynamicelements.Player;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javax.imageio.ImageIO.read;

public class Player2Visualizer implements DynamicElementVisualizer<Player> {
    private final Map<GUI.ACTION, List<String>> spriteMap;

    public Player2Visualizer() {
        spriteMap = new HashMap<>();
        spriteMap.put(GUI.ACTION.W, List.of("src/main/resources/images/players/lavena.png"));
        spriteMap.put(GUI.ACTION.A, List.of(
                "src/main/resources/images/players/lavena.png",
                "src/main/resources/images/players/lavena.png",
                "src/main/resources/images/players/lavena.png"));
        spriteMap.put(GUI.ACTION.D, List.of(
                "src/main/resources/images/players/lavena.png",
                "src/main/resources/images/players/lavena.png",
                "src/main/resources/images/players/lavena.png"));

        spriteMap.put(GUI.ACTION.S, List.of(
                "src/main/resources/images/players/lavena.png"));
    }

    @Override
    public void draw(Player player, GUI gui) throws IOException {
        String sprite = getSprite(player);
        BufferedImage loadedSprite = ImageIO.read(new File(sprite));
        if (loadedSprite != null)
            gui.drawImage(player.getPosition(), loadedSprite);
    }

    private String getSprite(Player player) {
        List<String> spriteActionList = spriteMap.get(player.getLastAction());
        return spriteActionList.get((int) (player.getLastActionCount() % spriteActionList.size()));
    }
}



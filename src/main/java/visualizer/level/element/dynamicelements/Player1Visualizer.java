package visualizer.level.element.dynamicelements;

import gui.GUI;
import model.elements.movingelements.Player;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javax.imageio.ImageIO.read;

public class Player2Vizualizer implements CharacterVisualizer<Player> {
    private final Map<GUI.ACTION, List<String>> spriteMap;

    public Player2Vizualizer(Player player) throws IOException {
        spriteMap = new HashMap<>();
        spriteMap.put(GUI.ACTION.UP, List.of("src/main/resources/images/players/lavena-jumping.png"));
        spriteMap.put(GUI.ACTION.LEFT, List.of(
                "src/main/resources/images/players/lavena-left-1.png",
                "src/main/resources/images/players/lavena-left-2.png",
                "src/main/resources/images/players/lavena-left-3.png"));
        spriteMap.put(GUI.ACTION.RIGHT, List.of(
                "src/main/resources/images/players/lavena-right-1.png",
                "src/main/resources/images/players/lavena-right-2.png",
                "src/main/resources/images/players/lavena-right-3.png"));

        spriteMap.put(GUI.ACTION.DOWN, List.of(
                "src/main/resources/images/players/lavena-standing.png"));
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
        return spriteActionList.get((int) (player.getLastControlCount() % spriteActionList.size()));
    }
}



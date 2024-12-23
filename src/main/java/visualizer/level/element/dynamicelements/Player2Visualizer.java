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


public class Player2Visualizer implements DynamicElementVisualizer<Player> {
    private final Map<Player.ORIENTATION, List<String>> spriteMap;

    public Player2Visualizer() {
        spriteMap = new HashMap<>();
        spriteMap.put(Player.ORIENTATION.UP, List.of("src/main/resources/images/players/lavena-jumping.png"));
        spriteMap.put(Player.ORIENTATION.LEFT, List.of("src/main/resources/images/players/lavena-left-1.png",
                "src/main/resources/images/players/lavena-left-2.png",
                "src/main/resources/images/players/lavena-left-3.png",
                "src/main/resources/images/players/lavena-left-4.png"));
        spriteMap.put(Player.ORIENTATION.DOWN, List.of("src/main/resources/images/players/lavena-jumping-down.png"));
        spriteMap.put(Player.ORIENTATION.RIGHT, List.of("src/main/resources/images/players/lavena-right-1.png",
                "src/main/resources/images/players/lavena-right-2.png",
                "src/main/resources/images/players/lavena-right-3.png",
                "src/main/resources/images/players/lavena-right-4.png"));
        spriteMap.put(Player.ORIENTATION.UPLEFT, List.of("src/main/resources/images/players/lavena-jumping-left.png"));
        spriteMap.put(Player.ORIENTATION.UPRIGHT, List.of("src/main/resources/images/players/lavena-jumping-right.png"));
        spriteMap.put(Player.ORIENTATION.DOWNLEFT, List.of("src/main/resources/images/players/lavena-jumping-left.png"));
        spriteMap.put(Player.ORIENTATION.DOWNRIGHT, List.of("src/main/resources/images/players/lavena-jumping-right.png"));
        spriteMap.put(Player.ORIENTATION.STANDING, List.of("src/main/resources/images/players/lavena-standing.png"));
    }

    @Override
    public void draw(Player player, GUI gui) throws IOException {
        String sprite = getSprite(player);
        BufferedImage loadedSprite = ImageIO.read(new File(sprite));
        if (loadedSprite != null)
            gui.drawImage(player.getPosition(), loadedSprite);
    }

    private String getSprite(Player player) {
        List<String> spriteActionList = spriteMap.get(player.getOrientation());
        return spriteActionList.get((int) (player.getLastActionCount()/2 % spriteActionList.size()));
    }
}



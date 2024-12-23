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

public class Player1Visualizer implements DynamicElementVisualizer<Player> {
    private final Map<Player.ORIENTATION, List<String>> spriteMap;

    public Player1Visualizer() {
        spriteMap = new HashMap<>();
        spriteMap.put(Player.ORIENTATION.UP, List.of("src/main/resources/images/players/tergon-jumping.png"));
        spriteMap.put(Player.ORIENTATION.LEFT, List.of("src/main/resources/images/players/tergon-left-1.png",
                "src/main/resources/images/players/tergon-left-2.png",
                "src/main/resources/images/players/tergon-left-3.png",
                "src/main/resources/images/players/tergon-left-4.png"));
        spriteMap.put(Player.ORIENTATION.DOWN, List.of("src/main/resources/images/players/tergon-jumping-down.png"));
        spriteMap.put(Player.ORIENTATION.RIGHT, List.of("src/main/resources/images/players/tergon-right-1.png",
                "src/main/resources/images/players/tergon-right-2.png",
                "src/main/resources/images/players/tergon-right-3.png",
                "src/main/resources/images/players/tergon-right-4.png"));
        spriteMap.put(Player.ORIENTATION.UPLEFT, List.of("src/main/resources/images/players/tergon-jumping-left.png"));
        spriteMap.put(Player.ORIENTATION.UPRIGHT, List.of("src/main/resources/images/players/tergon-jumping-right.png"));
        spriteMap.put(Player.ORIENTATION.DOWNLEFT, List.of("src/main/resources/images/players/tergon-jumping-left.png"));
        spriteMap.put(Player.ORIENTATION.DOWNRIGHT, List.of("src/main/resources/images/players/tergon-jumping-right.png"));
        spriteMap.put(Player.ORIENTATION.STANDING, List.of("src/main/resources/images/players/tergon-standing.png"));
    }

    @Override
    public void draw(Player player, GUI gui) throws IOException {
        String sprite = getSprite(player);
        BufferedImage loadedSprite;
        try {
            loadedSprite = ImageIO.read(new File(sprite));
        } catch (IOException e) {
            throw new IOException("Could not load sprite for Player 1 from path: " + sprite);
        }
        if (loadedSprite != null)
            gui.drawImage(player.getPosition(), loadedSprite);
    }

    private String getSprite(Player player) {
        List<String> spriteActionList = spriteMap.get(player.getOrientation());
        return spriteActionList.get((player.getLastActionCount()/2 % spriteActionList.size()));
    }
}




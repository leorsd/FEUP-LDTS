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

public class Player1Visualizer implements DynamicElementVisualizer<Player> {
    private final Map<Player.ORIENTATION, List<String>> spriteMap;

    public Player1Visualizer() {
        spriteMap = new HashMap<>();
        spriteMap.put(Player.ORIENTATION.UP, List.of("src/main/resources/images/players/tergon.png"));
        spriteMap.put(Player.ORIENTATION.LEFT, List.of("src/main/resources/images/players/tergon.png"));
        spriteMap.put(Player.ORIENTATION.DOWN, List.of("src/main/resources/images/players/tergon.png"));
        spriteMap.put(Player.ORIENTATION.RIGHT, List.of("src/main/resources/images/players/tergon.png"));
        spriteMap.put(Player.ORIENTATION.UPLEFT, List.of("src/main/resources/images/players/tergon.png"));
        spriteMap.put(Player.ORIENTATION.UPRIGHT, List.of("src/main/resources/images/players/tergon.png"));
        spriteMap.put(Player.ORIENTATION.DOWNLEFT, List.of("src/main/resources/images/players/tergon.png"));
        spriteMap.put(Player.ORIENTATION.DOWNRIGHT, List.of("src/main/resources/images/players/tergon.png"));
        spriteMap.put(Player.ORIENTATION.STANDING, List.of("src/main/resources/images/players/tergon.png"));
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
        return spriteActionList.get((int) (player.getLastActionCount() % spriteActionList.size()));
    }
}




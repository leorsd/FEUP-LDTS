package visualizer.level.element.dynamicelements;

import gui.GUI;
import model.elements.dynamicelements.Door;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DoorVisualizer implements DynamicElementVisualizer<Door> {
    private final Map<Door.STATE, String> spriteMap;

    public DoorVisualizer() {
        spriteMap = new HashMap<>();
        spriteMap.put(Door.STATE.OPEN, "src/main/resources/images/elements/closeddoor.png");
        spriteMap.put(Door.STATE.CLOSED, "src/main/resources/images/elements/door.png");
    }
    @Override
    public void draw(Door door, GUI gui) throws IOException {
        String sprite = getSprite(door);
        BufferedImage loadedSprite;
        try {
            loadedSprite = ImageIO.read(new File(sprite));
        } catch (IOException e) {
            throw new IOException("Could not load sprite for Door from path: " + sprite);
        }
        if (loadedSprite != null)
            gui.drawImage(door.getPosition(), loadedSprite);
    }

    private String getSprite(Door door) {
        return spriteMap.get(door.getState());
    }
}


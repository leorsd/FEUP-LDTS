package visualizer.level.element.dynamicelements;

import model.elements.dynamicelements.Monster;
import gui.GUI;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonsterVisualizer implements DynamicElementVisualizer<Monster> {
    private final Map<Monster.ORIENTATION, List<String>> spriteMap;

    public MonsterVisualizer() {
        spriteMap = new HashMap<>();
        spriteMap.put(Monster.ORIENTATION.LEFT, List.of("src/main/resources/images/elements/druid-left-1.png",
                "src/main/resources/images/elements/druid-left-2.png",
                "src/main/resources/images/elements/druid-left-3.png"));
        spriteMap.put(Monster.ORIENTATION.RIGHT, List.of("src/main/resources/images/elements/druid-right-1.png",
                "src/main/resources/images/elements/druid-right-2.png",
                "src/main/resources/images/elements/druid-right-3.png"));
        spriteMap.put(Monster.ORIENTATION.STANDING, List.of("src/main/resources/images/elements/druid-right-1.png"));
    }
    @Override
    public void draw(Monster monster, GUI gui) throws IOException {
        String sprite = getSprite(monster);
        BufferedImage loadedSprite = ImageIO.read(new File(sprite));
        if (loadedSprite != null)
            gui.drawImage(monster.getPosition(), loadedSprite);
    }

    private String getSprite(Monster monster) {
        List<String> spriteActionList = spriteMap.get(monster.getOrientation());
        return spriteActionList.get((monster.getLastControlCount()/5 % spriteActionList.size()));
    }
}

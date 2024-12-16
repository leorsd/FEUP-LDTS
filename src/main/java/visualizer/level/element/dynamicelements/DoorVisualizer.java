package visualizer.level.element.dynamicelements;

import gui.GUI;
import model.elements.dynamicelements.Door;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class DoorVisualizer implements DynamicElementVisualizer<Door> {
    @Override
    public void draw(Door door, GUI gui) {
        try {
            gui.drawImage(door.getPosition(), ImageIO.read(new File("src/main/resources/images/elements/door.png")).getSubimage(0, 0, door.getSizeX(), door.getSizeY()));
        } catch (IOException e) {

        }
    }
}

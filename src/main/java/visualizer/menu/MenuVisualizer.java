package visualizer.menu;

import model.Position;
import model.scenes.Menu;
import gui.GUI;
import visualizer.SceneVisualizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MenuVisualizer extends SceneVisualizer<Menu> {

    public MenuVisualizer(Menu menu) {
        super(menu);
    }

    @Override
    public void drawElements(GUI gui) throws IOException {
        int guiWidth= gui.getGUIWidth();
        int guiHeight= gui.getGUIHeight();
        File menuFile = new File("src/main/resources/images/menu/menu.png");
        BufferedImage menuimage;
        try {
            menuimage = ImageIO.read(menuFile);
        } catch (IOException e) {
            throw new IOException("Failed to load menu image when trying to draw menu");
        }
        gui.drawImage(new Position(0,0), menuimage);

        BufferedImage arrow = null;
        try {
            arrow = ImageIO.read(new File("src/main/resources/images/menu/arrow.png"));
        } catch (IOException e) {
            throw new IOException("Failed to load arrow image when trying to draw menu");
        }

        for (int i = 0; i < getScene().getEntriesSize(); i++) {
            BufferedImage image;
            try {
                image = ImageIO.read(new File("src/main/resources/images/menu/option" + i + ".png"));
            } catch (IOException e) {
                throw new IOException("Failed to load image for option: " + i + " when trying to draw menu");
            }
            if (i == getScene().getHighlightedEntryIndex()) {
                gui.drawImage(new Position((guiWidth-image.getWidth())/2, (int)(guiHeight*(0.50+0.08*i))), image);
                gui.drawImage(new Position((guiWidth-image.getWidth())/2-15, (int)(guiHeight*(0.50+0.08*i))), arrow);
            }else{
                gui.drawImage(new Position((guiWidth-image.getWidth())/2,(int)(guiHeight*(0.50+0.08*i))),image);
            }
        }
    }
}

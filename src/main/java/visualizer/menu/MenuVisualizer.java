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
        BufferedImage menuimage = null;
        try {
            menuimage = ImageIO.read(menuFile);
        } catch (IOException e) {
            throw new IOException("Failed to load menu image");
        }
        gui.drawImage(new Position(0,0), menuimage);

        if (getScene().getEntriesSize() != 6) {
            throw new IOException("Not enough images for the entries of the menu");
        }

        BufferedImage arrow = null;
        try {
            arrow = ImageIO.read(new File("src/main/resources/images/menu/arrow.png"));
        } catch (IOException e) {
            System.out.println("Failed arrow");
        }

        for (int i = 0; i < getScene().getEntriesSize(); i++) {
            try {
                BufferedImage image = ImageIO.read(new File("src/main/resources/images/menu/option" + i + ".png"));
                if (i == getScene().getHighlightedEntryIndex()) {
                    gui.drawImage(new Position((guiWidth-image.getWidth())/2, (int)(guiHeight*(0.50+0.08*i))), image);
                    gui.drawImage(new Position((guiWidth-image.getWidth())/2-15, (int)(guiHeight*(0.50+0.08*i))), arrow);
                }else{
                    gui.drawImage(new Position((guiWidth-image.getWidth())/2,(int)(guiHeight*(0.50+0.08*i))),image);
                }
            } catch (IOException e) {
                System.out.println("Failed option");
            }
        }
    }
}

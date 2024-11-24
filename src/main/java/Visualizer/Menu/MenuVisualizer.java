package Visualizer.Menu;

import Model.Position;
import Model.Scenes.Menu;
import GUI.GUI;
import Visualizer.SceneVisualizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MenuVisualizer extends SceneVisualizer<Menu> {

    public MenuVisualizer(Menu menu) {
        super(menu);
    }

    @Override
    public void drawElements(GUI gui) throws IOException {
        int guiWidth= gui.getGUIWidth();
        int guiHeight= gui.getGUIHeight();

        BufferedImage menuimage = ImageIO.read(new File("resources/images/menu.png"));
        gui.drawImage(new Position((guiWidth-menuimage.getWidth())/2, (int)(guiHeight*0.20)), menuimage);

        if (getScene().getEntriesSize() != 2) {
            throw new IOException("Not enough images for the entries of the menu");
        }

        BufferedImage arrow = ImageIO.read(new File("resources/images/arrow.png"));
        for (int i = 0; i < getScene().getEntriesSize(); i++) {
            BufferedImage image = ImageIO.read(new File("resources/images/option"+i+".png"));
            if (i == getScene().getHighlightedEntryIndex()) {
                gui.drawImage(new Position(guiWidth-image.getWidth()/2, (int)(guiHeight*(0.40+0.10*i))), image);
                gui.drawImage(new Position((guiWidth-image.getWidth()/2)-50, (int)(guiHeight*(0.40+0.10*i))), arrow);
            }else{
                gui.drawImage(new Position(guiWidth-image.getWidth()/2,(int)(guiHeight*(0.40+0.10*i))),image);
            }
        }
    }
}

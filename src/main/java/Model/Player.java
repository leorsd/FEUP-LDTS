package Model;

import java.awt.image.BufferedImage;

public class Player extends Character{
    private String name;

    public Player(String name, int sizeX, int sizeY, Position position, BufferedImage image) {
        super(sizeX, sizeY, position, image);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

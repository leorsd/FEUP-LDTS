package model.elements;

import model.Position;

import java.awt.image.BufferedImage;

public class ToggleableWall extends Wall {
    boolean isActive;

    public ToggleableWall(Position position, BufferedImage image, int sizeX, int sizeY) {
        super(position, image, sizeX, sizeY);
        this.isActive = true;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void toggle(){
        isActive = !isActive;
    }
}

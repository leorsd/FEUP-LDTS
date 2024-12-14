package model.elements.movingelements;

import model.Position;

import java.awt.image.BufferedImage;

public class Monster extends MovingElement {
    private int minX;
    private int maxX;
    private int direction = 1;

    public Monster(Position position, BufferedImage image, int sizeX, int sizeY, int minX, int maxX) {
        super(position, image, sizeX, sizeY);
        this.minX = minX;
        this.maxX = maxX;
    }

    public int getMinX() {
        return minX;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setMinX(int minX) {
        this.minX = minX;
    }

    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }
}

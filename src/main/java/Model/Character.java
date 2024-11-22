package Model;

import java.awt.image.BufferedImage;

public abstract class Character extends Element {
    private int sizeX;
    private int sizeY;

    public void move(Position position) {
        super.setPosition(position);
    }

    public Character(int sizeX, int sizeY, Position position, BufferedImage image) {
        super(position, image);
        this.sizeY = sizeY;
        this.sizeX = sizeX;
    }

    public int getSizeX() {
        return sizeX;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }
}

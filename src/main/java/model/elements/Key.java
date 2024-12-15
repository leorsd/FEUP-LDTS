package model.elements;

import model.Position;

import java.awt.image.BufferedImage;

public class Key extends Element{
    private String target;
    private boolean collected = false;

    public Key(Position position, BufferedImage image, int sizeX, int sizeY, String target) {
        super(position, image, sizeX, sizeY);
        this.target = target;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }
}

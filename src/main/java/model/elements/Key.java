package model.elements;

import model.Position;

import java.awt.image.BufferedImage;

public class Key extends Element{
    private String target;
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
}

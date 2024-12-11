package model.elements;

import model.Position;

import java.awt.image.BufferedImage;

public class Trap extends Element{
    private String target;

    public Trap(String target, Position position, BufferedImage image, int sizeX, int sizeY) {
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

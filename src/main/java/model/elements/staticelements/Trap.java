package model.elements.staticelements;

import model.Position;
import model.elements.Element;

import java.awt.image.BufferedImage;

public class Trap extends StaticElement {
    private String target;

    public Trap(String target, Position position, BufferedImage image, int sizeX, int sizeY) {
        super(position, sizeX, sizeY, image);
        this.target = target;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}

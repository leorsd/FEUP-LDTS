package model.elements.staticelements;

import model.Position;
import model.elements.Element;

import java.awt.image.BufferedImage;

public class Key extends StaticElement {
    private String target;
    private boolean collected = false;

    public Key(Position position, BufferedImage image, int sizeX, int sizeY, String target) {
        super(position, sizeX, sizeY, image);
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

package model.elements.staticelements;

import model.Position;

import java.awt.image.BufferedImage;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Key key)) return false;
        if (!super.equals(o)) return false;
        return collected == key.collected && Objects.equals(target, key.target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), target, collected);
    }
}

package model.elements.staticelements;

import model.Position;
import model.elements.Element;

import java.awt.image.BufferedImage;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trap trap)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(target, trap.target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), target);
    }
}

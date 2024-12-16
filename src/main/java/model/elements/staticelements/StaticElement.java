package model.elements.staticelements;

import model.Position;
import model.elements.Element;

import java.awt.image.BufferedImage;
import java.util.Objects;

public class StaticElement extends Element {
    BufferedImage image;

    public StaticElement(Position position, int sizeX, int sizeY, BufferedImage image) {
        super(position, sizeX, sizeY);
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StaticElement that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), image);
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}

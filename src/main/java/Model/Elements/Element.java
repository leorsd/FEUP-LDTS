package Model.Elements;

import Model.Position;

import java.awt.image.BufferedImage;
import java.util.Objects;

public abstract class Element {
    private Position position;
    private BufferedImage image;

    public Element(Position position, BufferedImage image) {
        this.position = position;
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Element element = (Element) o;
        return Objects.equals(position, element.position) && Objects.equals(image, element.image);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}

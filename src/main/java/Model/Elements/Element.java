package Model.Elements;

import Model.Position;

import java.awt.image.BufferedImage;
import java.util.Objects;

public abstract class Element {
    private Position position;
    private BufferedImage image;
    private int sizeX;
    private int sizeY;

    public Element(Position position, BufferedImage image, int sizeX, int sizeY) {
        this.position = position;
        this.image = image;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
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

    public boolean hasCollided(Position position, int otherSizeX, int otherSizeY) {
        int thisX = this.getPosition().getX();
        int thisY = this.getPosition().getY();
        int thisSizeX = this.getSizeX();
        int thisSizeY = this.getSizeY();
        return thisX < position.getX() + otherSizeX && thisX + thisSizeX > position.getX() &&
                thisY < position.getY() + otherSizeY && thisY + thisSizeY > position.getY();
    }
}
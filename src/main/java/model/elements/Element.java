package model.elements;

import model.Position;

import java.awt.image.BufferedImage;
import java.util.Objects;

public abstract class Element {
    private Position position;
    private int sizeX;
    private int sizeY;

    public Element(Position position, int sizeX, int sizeY) {
        this.position = position;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Element)) return false;
        Element element = (Element) o;
        return Objects.equals(position, element.position) && sizeX==element.sizeX && sizeY==element.sizeY;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, sizeX, sizeY);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
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

    public boolean isInside(Element otherElement) {
        int thisX = this.getPosition().getX();
        int thisY = this.getPosition().getY();
        int otherX = otherElement.getPosition().getX();
        int otherY = otherElement.getPosition().getY();
        if (thisX < otherX || thisY < otherY) return false;
        return thisX + this.getSizeX() <= otherX + otherElement.getSizeX() && thisY + this.getSizeY() <= otherY + otherElement.getSizeY();
    }
}
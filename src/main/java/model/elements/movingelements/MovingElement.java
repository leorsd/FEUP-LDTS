package model.elements.movingelements;

import model.elements.Element;
import model.Position;

import java.awt.image.BufferedImage;

public abstract class MovingElement extends Element {
    public MovingElement(Position position, BufferedImage image, int sizeX, int sizeY) {
        super(position, image, sizeX, sizeY);
    }
}

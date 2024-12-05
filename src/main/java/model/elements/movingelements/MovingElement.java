package Model.Elements.MovingElements;

import Model.Elements.Element;
import Model.Position;

import java.awt.image.BufferedImage;

public abstract class MovingElement extends Element {
    public MovingElement(Position position, BufferedImage image, int sizeX, int sizeY) {
        super(position, image, sizeX, sizeY);
    }
}

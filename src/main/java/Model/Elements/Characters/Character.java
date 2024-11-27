package Model.Elements.Characters;

import Model.Elements.Element;
import Model.Position;

import java.awt.image.BufferedImage;

public abstract class Character extends Element {
    public Character(Position position, BufferedImage image, int sizeX, int sizeY) {
        super(position, image, sizeX, sizeY);
    }
}

package model.elements.staticelements;

import model.Position;
import model.elements.Element;

import java.awt.image.BufferedImage;

public class Wall extends StaticElement {

    public Wall(Position position, BufferedImage image, int sizeX, int sizeY) {
        super(position, sizeX, sizeY, image);
    }
}

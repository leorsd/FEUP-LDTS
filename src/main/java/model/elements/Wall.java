package model.elements;

import model.Position;

import java.awt.image.BufferedImage;

public class Wall extends Element{

    public Wall(Position position, BufferedImage image, int sizeX, int sizeY) {
        super(position, image, sizeX, sizeY);
    }
}

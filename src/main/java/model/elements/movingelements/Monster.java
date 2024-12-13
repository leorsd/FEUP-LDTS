package model.elements.movingelements;

import model.Position;

import java.awt.image.BufferedImage;

public class Monster extends MovingElement {

    public Monster(Position position, BufferedImage image, int sizeX, int sizeY) {
        super(position, image, sizeX, sizeY);
    }
}

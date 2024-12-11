package model.elements.movingelements;

import model.Position;

import java.awt.image.BufferedImage;

public class Player extends MovingElement {
    private String name;

    public Player(String name, int sizeX, int sizeY, Position position, BufferedImage image) {
        super(position, image, sizeX, sizeY);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

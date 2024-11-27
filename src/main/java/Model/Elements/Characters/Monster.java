package Model.Elements.Characters;

import Model.Position;

import java.awt.image.BufferedImage;

public class Monster extends Character {
    private int health;

    public Monster(int health, Position position, BufferedImage image, int sizeX, int sizeY) {
        super(position, image, sizeX, sizeY);
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}

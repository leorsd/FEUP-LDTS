package Model.Elements.Characters;

import Model.Position;

import java.awt.image.BufferedImage;

public class Monster extends Character{
    private int health;

    public Monster(int health, int sizeX, int sizeY, Position position, BufferedImage image) {
        super(sizeX, sizeY, position, image);
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}

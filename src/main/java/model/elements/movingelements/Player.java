package model.elements.movingelements;

import model.Position;

import java.awt.image.BufferedImage;

public class Player extends MovingElement {
    private String name;
    private int speed;
    private int maxJumpHeight;
    private Position spawnPosition;

    public Player(String name, int sizeX, int sizeY, Position position, BufferedImage image, int speed,  int maxJumpHeight) {
        super(position, image, sizeX, sizeY);
        this.name = name;
        this.speed = speed;
        this.maxJumpHeight = maxJumpHeight;
        this.spawnPosition = position;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getMaxJumpHeight() {
        return maxJumpHeight;
    }

    public void setMaxJumpHeight(int maxJumpHeight) {
        this.maxJumpHeight = maxJumpHeight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Position getSpawnPosition() { return this.spawnPosition; }

    public void setSpawnPosition(Position spawnPosition) { this.spawnPosition = spawnPosition; }
}

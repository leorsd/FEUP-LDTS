package model.elements.dynamicelements;

import model.Position;

public class Player extends DynamicElement {
    public enum ORIENTATION {UP, DOWN, LEFT, RIGHT, UPLEFT, UPRIGHT, DOWNLEFT, DOWNRIGHT, STANDING}
    private String name;
    private int speed;
    private int maxJumpHeight;
    private Player.ORIENTATION orientation = ORIENTATION.STANDING;
    private int lastActionCount = 0;

    public Player(String name, int sizeX, int sizeY, Position position, int speed,  int maxJumpHeight) {
        super(position, sizeX, sizeY);
        this.name = name;
        this.speed = speed;
        this.maxJumpHeight = maxJumpHeight;
    }

    public ORIENTATION getOrientation() {
        return orientation;
    }

    public void setOrientation(ORIENTATION orientation) {
        this.orientation = orientation;
    }

    public int getLastActionCount() { return lastActionCount; }

    public void setLastActionCount(int lastActionCount) { this.lastActionCount = lastActionCount; }

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

}

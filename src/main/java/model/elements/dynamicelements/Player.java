package model.elements.dynamicelements;

import model.Position;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player player)) return false;
        if (!super.equals(o)) return false;
        return speed == player.speed && maxJumpHeight == player.maxJumpHeight && lastActionCount == player.lastActionCount && Objects.equals(name, player.name) && orientation == player.orientation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, speed, maxJumpHeight, orientation, lastActionCount);
    }
}

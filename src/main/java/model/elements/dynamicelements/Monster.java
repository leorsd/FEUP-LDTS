package model.elements.dynamicelements;

import model.Position;

import java.util.Objects;

public class Monster extends DynamicElement {
    public enum ORIENTATION {LEFT, RIGHT, STANDING}
    private int minX;
    private int maxX;
    private int direction = 1;
    private Monster.ORIENTATION orientation;
    private int lastControlCount;

    public Monster(Position position, int sizeX, int sizeY, int minX, int maxX) {
        super(position, sizeX, sizeY);
        this.minX = minX;
        this.maxX = maxX;
        this.orientation = Monster.ORIENTATION.RIGHT;
    }

    public ORIENTATION getOrientation() {
        return orientation;
    }

    public void setOrientation(ORIENTATION orientation) {
        this.orientation = orientation;
    }

    public int getLastControlCount() { return lastControlCount; }

    public void setLastControlCount(int lastControlCount) { this.lastControlCount = lastControlCount; }

    public int getMinX() {
        return minX;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setMinX(int minX) {
        this.minX = minX;
    }

    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Monster monster)) return false;
        if (!super.equals(o)) return false;
        return minX == monster.minX && maxX == monster.maxX && direction == monster.direction && lastControlCount == monster.lastControlCount && orientation == monster.orientation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), minX, maxX, direction, orientation, lastControlCount);
    }
}

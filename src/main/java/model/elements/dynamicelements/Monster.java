package model.elements.dynamicelements;

import gui.GUI;
import model.Position;

public class Monster extends DynamicElement {
    private int minX;
    private int maxX;
    private int direction = 1;
    private GUI.ACTION lastAction;
    private int lastControlCount;

    public Monster(Position position, int sizeX, int sizeY, int minX, int maxX, GUI.ACTION firstAction) {
        super(position, sizeX, sizeY);
        this.minX = minX;
        this.maxX = maxX;
        this.lastAction = firstAction;
    }

    public GUI.ACTION getLastAction() { return lastAction; }

    public void setLastAction(GUI.ACTION lastAction) { this.lastAction = lastAction; }

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
}

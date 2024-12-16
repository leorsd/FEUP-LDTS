package model.elements.dynamicelements;

import gui.GUI;
import model.Position;

import java.awt.image.BufferedImage;

public class Player extends DynamicElement {
    private String name;
    private int speed;
    private int maxJumpHeight;
    private GUI.ACTION lastAction;
    private int lastActionCount = 0;

    public Player(String name, int sizeX, int sizeY, Position position, int speed,  int maxJumpHeight, GUI.ACTION firstAction) {
        super(position, sizeX, sizeY);
        this.name = name;
        this.speed = speed;
        this.maxJumpHeight = maxJumpHeight;
        this.lastAction = firstAction;
    }

    public GUI.ACTION getLastAction() { return lastAction; }

    public void setLastAction(GUI.ACTION lastAction) { this.lastAction = lastAction; }

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

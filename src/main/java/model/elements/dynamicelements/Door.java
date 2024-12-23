package model.elements.dynamicelements;

import model.Position;

public class Door extends DynamicElement {
    public enum STATE {OPEN, CLOSED}
    private STATE state = STATE.CLOSED;
    public Door(Position position, int sizeX, int sizeY) {
        super(position, sizeX, sizeY);
    }

    public STATE getState() {
        return state;
    }

    public void setState(STATE state) {
        this.state = state;
    }
}

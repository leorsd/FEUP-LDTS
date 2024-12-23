package model.elements.dynamicelements;

import model.Position;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Door door)) return false;
        if (!super.equals(o)) return false;
        return state == door.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), state);
    }
}

package model.elements;

import model.Position;

import java.awt.image.BufferedImage;
import java.util.Objects;

public class ToggleableWall extends Wall {
    boolean isActive;

    public ToggleableWall(Position position, BufferedImage image, int sizeX, int sizeY) {
        super(position, image, sizeX, sizeY);
        this.isActive = true;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void toggle(){
        isActive = !isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ToggleableWall that)) return false;
        if (!super.equals(o)) return false;
        return isActive == that.isActive;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), isActive);
    }
}

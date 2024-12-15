package model.elements;

import model.Position;

import java.awt.image.BufferedImage;

public class Button extends Element {
    ToggleableWall toggleableWall;
    boolean isPressed = false;

    public Button(Position position, BufferedImage image, int sizeX, int sizeY, ToggleableWall toggleableWall) {
        super(position, image, sizeX, sizeY);
        this.toggleableWall = toggleableWall;
    }

    public ToggleableWall getToggleableWall() {
        return toggleableWall;
    }

    public void setToggleableWall(ToggleableWall toggleableWall) {
        this.toggleableWall = toggleableWall;
    }

    public boolean isPressed() {
        return isPressed;
    }

    public void setPressed(boolean pressed) {
        isPressed = pressed;
    }

    public void press() {
        isPressed = !isPressed;
        toggleableWall.toggle();
    }
}

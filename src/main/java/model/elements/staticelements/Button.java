package model.elements.staticelements;

import model.Position;
import model.elements.Element;

import java.awt.image.BufferedImage;

public class Button extends StaticElement {
    ToggleableWall toggleableWall;
    boolean isPressed = false;

    public Button(Position position, BufferedImage image, int sizeX, int sizeY, ToggleableWall toggleableWall) {
        super(position, sizeX, sizeY, image);
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

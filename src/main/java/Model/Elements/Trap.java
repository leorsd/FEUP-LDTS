package Model.Elements;

import Model.Position;

import java.awt.image.BufferedImage;

public class Trap extends Element{
    private String target;
    private String color;

    public Trap(String target, String color, Position position) {
        super(position, null);
        this.target = target;
        this.color = color;
    }

    public Trap(String target, Position position, BufferedImage image) {
        super(position, image);
        this.target = target;
        this.color = null;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        if (color!=null) {
            this.color = color;
        }
    }
}

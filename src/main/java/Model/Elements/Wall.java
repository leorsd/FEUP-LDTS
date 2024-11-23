package Model.Elements;

import Model.Position;

import java.awt.image.BufferedImage;

public class Wall extends Element{
    private String color;

    public Wall(String color, Position position) {
        super(position, null);
        this.color = color;
    }

    public Wall(Position position, BufferedImage image) {
        super(position, image);
        this.color = null;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        if (color != null) {
            this.color = color;
        }
    }
}

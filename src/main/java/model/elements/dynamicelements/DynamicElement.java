package model.elements.dynamicelements;

import model.elements.Element;
import model.Position;


public abstract class DynamicElement extends Element {
    public DynamicElement(Position position, int sizeX, int sizeY) {
        super(position, sizeX, sizeY);
    }
}

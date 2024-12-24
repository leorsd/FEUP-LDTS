package visualizer.level.element.staticelements;

import model.elements.staticelements.Trap;
import gui.GUI;

public class TrapVisualizer implements StaticElementVisualizer<Trap> {
    @Override
    public void draw(Trap trap, GUI gui){
        gui.drawImage(trap.getPosition(), trap.getImage());
    }
}

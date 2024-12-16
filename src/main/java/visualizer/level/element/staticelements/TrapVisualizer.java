package visualizer.level.element.staticelements;

import model.elements.staticelements.Trap;
import gui.GUI;
import visualizer.level.element.ElementVisualizer;

public class TrapVisualizer implements ElementVisualizer<Trap> {
    @Override
    public void draw(Trap trap, GUI gui){
        gui.drawImage(trap.getPosition(), trap.getImage());
    }
}

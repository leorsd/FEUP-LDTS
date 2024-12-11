package visualizer.level.element;

import model.elements.Trap;
import gui.GUI;

public class TrapVisualizer implements ElementVisualizer<Trap> {
    @Override
    public void draw(Trap trap, GUI gui){
        gui.drawTrap(trap);
    }
}

package Visualizer.Level.Element;

import Model.Elements.Trap;
import GUI.GUI;

public class TrapVisualizer implements ElementVisualizer<Trap> {
    @Override
    public void draw(Trap trap, GUI gui){
        gui.drawTrap(trap);
    }
}

package visualizer.level.element;

import gui.GUI;
import model.elements.Button;

public class ButtonVisualizer implements ElementVisualizer<Button> {
    @Override
    public void draw(Button button, GUI gui) {
        if (!button.isPressed()) gui.drawButton(button);
    }
}
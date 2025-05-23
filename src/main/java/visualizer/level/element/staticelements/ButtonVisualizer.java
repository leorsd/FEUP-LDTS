package visualizer.level.element.staticelements;

import gui.GUI;
import model.elements.staticelements.Button;

public class ButtonVisualizer implements StaticElementVisualizer<Button> {
    @Override
    public void draw(Button button, GUI gui) {
        if (!button.isPressed()) gui.drawImage(button.getPosition(), button.getImage());
    }
}
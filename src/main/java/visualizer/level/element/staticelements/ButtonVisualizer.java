package visualizer.level.element.staticelements;

import gui.GUI;
import model.elements.staticelements.Button;
import visualizer.level.element.ElementVisualizer;

public class ButtonVisualizer implements ElementVisualizer<Button> {
    @Override
    public void draw(Button button, GUI gui) {
        if (!button.isPressed()) gui.drawImage(button.getPosition(), button.getImage());
    }
}
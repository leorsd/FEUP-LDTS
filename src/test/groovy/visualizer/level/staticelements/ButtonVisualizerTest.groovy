package visualizer.level.staticelements

import gui.GUI
import model.Position
import model.elements.staticelements.Button
import model.elements.staticelements.ToggleableWall
import spock.lang.Specification
import visualizer.level.element.staticelements.ButtonVisualizer

class ButtonVisualizerTest extends Specification{
    def "test draw draws button correctly when not pressed"() {
        given:
            def gui = Mock(GUI)
            def toggleableWall = Mock(ToggleableWall)
            def button = new Button(new Position(0, 0), null, 10, 10, toggleableWall as ToggleableWall)
            def buttonVisualizer = new ButtonVisualizer()
        when:
            buttonVisualizer.draw(button, gui as GUI)
        then:
            1 * gui.drawImage(button.getPosition(), button.getImage())
    }

    def "test draw does not draw button when pressed"() {
        given:
            def gui = Mock(GUI)
            def toggleableWall = Mock(ToggleableWall)
            def button = new Button(new Position(0, 0), null, 10, 10, toggleableWall)
            def buttonVisualizer = new ButtonVisualizer()
        when:
            button.setPressed(true)
            buttonVisualizer.draw(button, gui as GUI)
        then:
            0 * gui.drawImage(_, _)
    }
}

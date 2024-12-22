package visualizer.level.staticelements

import gui.GUI
import model.Position
import model.elements.staticelements.Key
import spock.lang.Specification
import visualizer.level.element.staticelements.KeyVisualizer

class KeyVisualizerTest extends Specification{

    def "test draw draws key correctly when not collected"() {
        given:
            def gui = Mock(GUI)
            def key = new Key(new Position(0, 0), null, 10, 10, "Tergon")
            def keyVisualizer = new KeyVisualizer()
        when:
            keyVisualizer.draw(key, gui as GUI)
        then:
            1 * gui.drawImage(key.getPosition(), key.getImage())
    }

    def "test draw does not draw key when collected"() {
        given:
            def gui = Mock(GUI)
            def key = new Key(new Position(0, 0), null, 10, 10, "Lavena")
            def keyVisualizer = new KeyVisualizer()
        when:
            key.setCollected(true)
            keyVisualizer.draw(key, gui as GUI)
        then:
            0 * gui.drawImage(_, _)
    }
}

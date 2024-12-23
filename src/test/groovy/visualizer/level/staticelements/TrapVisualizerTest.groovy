package visualizer.level.staticelements

import gui.GUI
import model.Position
import model.elements.staticelements.Trap
import spock.lang.Specification
import visualizer.level.element.staticelements.TrapVisualizer

class TrapVisualizerTest extends Specification{

    def "test draw draws trap correctly"() {
        given:
            def gui = Mock(GUI)
            def trap = new Trap("TrapName", new Position(0, 0), null, 10, 10)
            def trapVisualizer = new TrapVisualizer()
        when:
            trapVisualizer.draw(trap, gui as GUI)
        then:
            1 * gui.drawImage(trap.getPosition(), trap.getImage())
    }
}

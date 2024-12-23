package visualizer.level.dynamicelements

import gui.GUI
import model.Position
import model.elements.dynamicelements.Door
import spock.lang.Specification
import visualizer.level.element.dynamicelements.DoorVisualizer

class DoorVisualizerTest extends Specification{
    def "test draw draws door correctly based on its state"() {
        given:
            def gui = Mock(GUI)
            def doorVisualizer = new DoorVisualizer()
            def door = new Door(new Position(20, 20),10,10)
        when:
            door.setState(Door.STATE.CLOSED)
            doorVisualizer.draw(door, gui as GUI)
        then:
            doorVisualizer.getSprite(door) == "src/main/resources/images/elements/door.png"
            1 * gui.drawImage(door.getPosition(), _)
        when:
            door.setState(Door.STATE.OPEN)
            doorVisualizer.draw(door, gui as GUI)
        then:
            doorVisualizer.getSprite(door) == "src/main/resources/images/elements/closeddoor.png"
            1 * gui.drawImage(door.getPosition(), _)
    }
}

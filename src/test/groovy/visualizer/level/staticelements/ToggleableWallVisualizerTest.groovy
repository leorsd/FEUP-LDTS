package visualizer.level.staticelements

import gui.GUI
import model.Position
import model.elements.staticelements.ToggleableWall
import spock.lang.Specification
import visualizer.level.element.staticelements.ToggleableWallVisualizer

class ToggleableWallVisualizerTest extends Specification{

    def "test draw draws toggleable wall correctly when active"() {
        given:
            def gui = Mock(GUI)
            def toggleableWall = new ToggleableWall(new Position(0, 0), null, 10, 10)
            def toggleableWallVisualizer = new ToggleableWallVisualizer()
        when:
            toggleableWallVisualizer.draw(toggleableWall, gui as GUI)
        then:
            1 * gui.drawImage(toggleableWall.getPosition(), toggleableWall.getImage())
    }

    def "test draw does not draw toggleable wall when inactive"() {
        given:
            def gui = Mock(GUI)
            def toggleableWall = new ToggleableWall(new Position(0, 0), null, 10, 10)
            def toggleableWallVisualizer = new ToggleableWallVisualizer()
        when:
            toggleableWall.toggle()
            toggleableWallVisualizer.draw(toggleableWall, gui as GUI)
        then:
            0 * gui.drawImage(_, _)
    }
}

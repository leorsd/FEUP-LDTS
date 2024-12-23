package visualizer.level.staticelements

import gui.GUI
import model.Position
import model.elements.staticelements.Wall
import spock.lang.Specification
import visualizer.level.element.staticelements.WallVisualizer

class WallVisualizerTest extends Specification{

    def "test drawElements draws walls correctly"() {
        given:
            def gui = Mock(GUI)
            def wall = new Wall(new Position(0, 0), null, 10, 10)
            def wallVisualizer = new WallVisualizer()
        when:
            wallVisualizer.draw(wall, gui)
        then:
            1 * gui.drawImage(wall.getPosition(), wall.getImage())
    }
}

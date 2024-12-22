package visualizer.level.dynamicelements

import model.Position
import spock.lang.Specification
import visualizer.level.element.dynamicelements.Player2Visualizer
import model.elements.dynamicelements.Player
import gui.GUI

class Player2VisualizerTest extends Specification {
    def "test draw draws player2 correctly based on orientation and last action count"() {
        given:
            def gui = Mock(GUI)
            def player2Visualizer = new Player2Visualizer()
            def player = new Player("Lavena", 10, 10, new Position(20, 20), 2, 20)
        when:
            player.setOrientation(Player.ORIENTATION.UP)
            player.setLastActionCount(0)
            player2Visualizer.draw(player, gui as GUI)
        then:
            player2Visualizer.getSprite(player) == "src/main/resources/images/players/lavena-jumping.png"
            1 * gui.drawImage(player.getPosition(), _)
        when:
            player.setOrientation(Player.ORIENTATION.LEFT)
            player.setLastActionCount(0)
            player2Visualizer.draw(player, gui as GUI)
        then:
            player2Visualizer.getSprite(player) == "src/main/resources/images/players/lavena-left-1.png"
            1 * gui.drawImage(player.getPosition(), _)
        when:
            player.setOrientation(Player.ORIENTATION.DOWN)
            player.setLastActionCount(0)
            player2Visualizer.draw(player, gui as GUI)
        then:
            player2Visualizer.getSprite(player) == "src/main/resources/images/players/lavena-jumping-down.png"
            1 * gui.drawImage(player.getPosition(), _)
    }
}

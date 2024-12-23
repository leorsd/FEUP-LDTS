package visualizer.level.dynamicelements

import gui.GUI
import model.Position
import model.elements.dynamicelements.Player
import spock.lang.Specification
import visualizer.level.element.dynamicelements.Player1Visualizer

class Player1VisualizerTest extends Specification{
    def "test draw draws player1 correctly based on orientation and last action count"() {
        given:
            def gui = Mock(GUI)
            def player1Visualizer = new Player1Visualizer()
            def player = new Player("Tergon", 10, 10, new Position(20, 20), 2, 20)
        when:
            player.setOrientation(Player.ORIENTATION.UP)
            player.setLastActionCount(0)
            player1Visualizer.draw(player, gui as GUI)
        then:
            player1Visualizer.getSprite(player) == "src/main/resources/images/players/tergon-jumping.png"
            1 * gui.drawImage(player.getPosition(), _)
        when:
            player.setOrientation(Player.ORIENTATION.LEFT)
            player.setLastActionCount(0)
            player1Visualizer.draw(player, gui as GUI)
        then:
            player1Visualizer.getSprite(player) == "src/main/resources/images/players/tergon-left-1.png"
            1 * gui.drawImage(player.getPosition(), _)
        when:
            player.setOrientation(Player.ORIENTATION.DOWN)
            player.setLastActionCount(0)
            player1Visualizer.draw(player, gui as GUI)
        then:
            player1Visualizer.getSprite(player) == "src/main/resources/images/players/tergon-jumping-down.png"
            1 * gui.drawImage(player.getPosition(), _)
    }
}

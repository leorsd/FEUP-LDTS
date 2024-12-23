package visualizer.level

import model.Position
import model.elements.dynamicelements.Door
import model.elements.dynamicelements.Monster
import model.elements.dynamicelements.Player
import model.elements.staticelements.Button
import model.elements.staticelements.Key
import model.elements.staticelements.ToggleableWall
import model.elements.staticelements.Trap
import model.elements.staticelements.Wall
import spock.lang.Specification
import gui.GUI
import model.scenes.Level

class LevelVisualizerTest extends Specification{
    def "test drawElements correctly draws all elements in the level"() {
        given:
            def gui = Mock(GUI)
            def level = Mock(Level)
            def levelVisualizer = new LevelVisualizer(level)
            level.getWalls() >> [new Wall(new Position(1, 1), null,10,10)]
            level.getToggleableWalls() >> [new ToggleableWall(new Position(2, 2), null, 10 ,10)]
            level.getButtons() >> [new Button(new Position(3, 3), null, 10, 10, null)]
            level.getKeys() >> [new Key(new Position(4, 4), null, 10, 10, "Tergon")]
            level.getMonsters() >> [new Monster(new Position(5, 5), 10, 10,0,20)]
            level.getLevelEndingDoor() >> new Door(new Position(6, 6),10,10)
            level.getPlayer1() >> new Player("Player1", 10, 10, new Position(7, 7), 2, 20)
            level.getPlayer2() >> new Player("Player2", 10, 10, new Position(8, 8), 2, 20)
            level.getTraps() >> [new Trap("Tergon",new Position(9, 9), null, 10, 10)]
        when:
            levelVisualizer.drawElements(gui)
        then:
            1 * gui.drawImage(new Position(1, 1), _)
            1 * gui.drawImage(new Position(2, 2), _)
            1 * gui.drawImage(new Position(3, 3), _)
            1 * gui.drawImage(new Position(4, 4), _)
            1 * gui.drawImage(new Position(5, 5), _)
            1 * gui.drawImage(new Position(6, 6), _)
            1 * gui.drawImage(new Position(7, 7), _)
            1 * gui.drawImage(new Position(8, 8), _)
            1 * gui.drawImage(new Position(9, 9), _)
        and:
            1 * gui.drawImage(new Position(0, 0), _)
    }
}

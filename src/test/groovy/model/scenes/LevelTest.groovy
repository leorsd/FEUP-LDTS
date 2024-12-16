package model.scenes

import model.elements.dynamicelements.Door
import model.elements.staticelements.Button
import model.elements.staticelements.ToggleableWall
import model.elements.dynamicelements.Monster
import model.elements.staticelements.Key
import model.elements.staticelements.Trap
import model.Position
import spock.lang.Specification
import model.elements.dynamicelements.Player
import model.elements.staticelements.Wall

class LevelTest extends Specification {

    def "should correctly initialize with provided elements"() {
        given:"lists of walls, monsters, traps, keys, and players"
        def walls = [new Wall(new Position(1, 1), null, 1,1)]
        def monsters = [new Monster(new Position(2, 2), 1, 1, 1,1, null)]
        def toggleableWall = new ToggleableWall(new Position(2,3), null, 10, 10)
        def toggleableWalls = [toggleableWall]
        def buttons = [new Button(new Position(10,10), null, 10, 10, toggleableWall)]
        def traps = [new Trap("Lavena", new Position(3, 3), null, 1,1)]
        def keys = [new Key(new Position(4, 4), null, 1,1,"Lavena")]
        def player1 = new Player("Lavena",1,1, new Position(5, 5),  2, 10, null)
        def player2 = new Player("Tergon",1,1, new Position(6, 6), 2, 10, null)
        def levelTransitionWall = new Door(new Position(1, 1), 1,1);

        when:"a Level is created"
        def level = new Level(walls,toggleableWalls, buttons, monsters, traps, keys, player1, player2, 10, 10, null, levelTransitionWall,"src/main/resources/Levels/level2")

        then:"all elements should be initialized correctly"
        level.getWalls() == walls
        level.getMonsters() == monsters
        level.getTraps() == traps
        level.getKeys() == keys
        level.getPlayer1() == player1
        level.getPlayer2() == player2
        level.getxBoundary() == 10
        level.getyBoundary() == 10
        level.getLevelEndingDoor() == levelTransitionWall
        level.getToggleableWalls() == toggleableWalls
        level.getButtons() == buttons
    }

    def "test isPositionFree with no walls or obstacles"() {
        given: "a level with no walls, monsters, traps, or keys"
        def player1 = Mock(Player)
        def player2 = Mock(Player)
        def walls = []
        def buttons = []
        def toggleableWalls = []
        def monsters = []
        def traps = []
        def keys = []
        def level = new Level(walls, toggleableWalls, buttons, monsters, traps, keys, player1, player2, 10, 10,null,null, "src/main/resources/Levels/level2")

        and: "a free position inside the boundaries"
        def position = new Position(5, 5)

        expect: "the position should be free"
        level.isPositionFree(position)
    }

    def "test isPositionFree with a wall at the position"() {
        given: "a level with one wall"
        def player1 = Mock(Player)
        def player2 = Mock(Player)
        def wall = new Wall(new Position(5, 5), null, 1, 1)
        def toggleableWalls = []
        def buttons = []
        def walls = [wall]
        def monsters = []
        def traps = []
        def keys = []
        def level = new Level(walls, toggleableWalls, buttons, monsters, traps, keys, player1, player2, 10, 10,null, null, "src/main/resources/Levels/level2")

        and: "a position where a wall is present"
        def position = new Position(5, 5)

        expect: "the position should not be free"
        !level.isPositionFree(position)
    }

    def "test boundary check"() {
        given: "a level with boundaries"
        def player1 = Mock(Player)
        def player2 = Mock(Player)
        def walls = []
        def toggleableWalls = []
        def buttons = []
        def monsters = []
        def traps = []
        def keys = []
        def level = new Level(walls, toggleableWalls, buttons, monsters, traps, keys, player1, player2, 5, 5,null,null, "src/main/resources/Levels/level2")

        expect: "positions outside the boundaries should be marked as occupied"
        !level.isPositionFree(new Position(-1, 0))
        !level.isPositionFree(new Position(0, -1))
        !level.isPositionFree(new Position(5, 0))
        !level.isPositionFree(new Position(0, 5))
    }

    def "in a level with boundaries initialized as 0, all positions should be out of bounds"() {
        given: "a level with boundaries initialized as 0"
        def player1 = Mock(Player)
        def player2 = Mock(Player)
        def walls = []
        def toggleableWalls = []
        def buttons = []
        def monsters = []
        def traps = []
        def keys = []
        def level = new Level(walls, toggleableWalls, buttons, monsters, traps, keys, player1, player2, 0, 0,null,null, "src/main/resources/Levels/level2")

        expect: "no position should be free"
        !level.isPositionFree(new Position(0, 0))
        !level.isPositionFree(new Position(1, 0))
        !level.isPositionFree(new Position(0, 1))
        !level.isPositionFree(new Position(-1, -1))
        !level.isPositionFree(new Position(5, 5))
    }
}


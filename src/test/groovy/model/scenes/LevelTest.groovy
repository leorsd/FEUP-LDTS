package model.scenes

import model.elements.dynamicelements.Door
import model.elements.dynamicelements.Monster
import model.elements.dynamicelements.Player
import model.Position
import model.elements.staticelements.*
import spock.lang.Specification

import java.awt.image.BufferedImage

class LevelTest extends Specification {

    def player1
    def player2
    def wall
    def toggleableWall
    def toggleableWalls
    def buttons
    def traps
    def keys
    def level
    def levelTransitionWall

    def setup() {
        player1 = new Player("Lavena", 1, 1, new Position(5, 5), 2, 10)
        player2 = new Player("Tergon", 1, 1, new Position(6, 6), 2, 10)
        wall = new Wall(new Position(5, 5), null, 1, 1)
        toggleableWall = new ToggleableWall(new Position(5, 5), null, 1, 1)
        toggleableWalls = [toggleableWall]
        buttons = [new Button(new Position(10, 10), null, 10, 10, toggleableWall)]
        traps = [new Trap("Lavena", new Position(3, 3), null, 1, 1)]
        keys = [new Key(new Position(4, 4), null, 1, 1, "Lavena")]
        levelTransitionWall = new Door(new Position(1, 1), 1, 1)

        level = new Level([wall], toggleableWalls, buttons, [], traps, keys, player1, player2, 10, 10, null, levelTransitionWall, "src/main/resources/Levels/level2")
    }

    def "should initialize correctly with all elements"() {
        when:
            level = new Level([wall], toggleableWalls, buttons, [new Monster(new Position(2, 2), 1, 1, 1, 1)], traps, keys, player1, player2, 10, 10, null, levelTransitionWall, "src/main/resources/Levels/level2")
        then:
            level.getWalls() == [wall]
            level.getMonsters() == [new Monster(new Position(2, 2), 1, 1, 1, 1)]
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

    def "should correctly handle position checks for out of bounds"() {
        given:
            def levelWithBoundaries = new Level([], [], [], [], [], [], Mock(Player), Mock(Player), 10, 10, null, null, "src/main/resources/Levels/level2")
        expect:
            !levelWithBoundaries.isPositionFree(new Position(-1, 0))
            !levelWithBoundaries.isPositionFree(new Position(0, -1))
            !levelWithBoundaries.isPositionFree(new Position(10, 0))
            !levelWithBoundaries.isPositionFree(new Position(0, 10))
            levelWithBoundaries.isPositionFree(new Position(0,0))
    }

    def "should correctly handle position inside boundaries and without obstacles"() {
        given:
            def emptyLevel = new Level([], [], [], [], [], [], Mock(Player), Mock(Player), 10, 10, null, null, "src/main/resources/Levels/level2")
        expect:
            emptyLevel.isPositionFree(new Position(5, 5))
    }

    def "should not allow position to be free if there is a wall at the position"() {
        given:
            def levelWithWall = new Level([wall], [], [], [], [], [], Mock(Player), Mock(Player), 10, 10, null, null, "src/main/resources/Levels/level2")
        expect:
            !levelWithWall.isPositionFree(new Position(5, 5))
    }

    def "should not allow position to be free if there is a toggleable wall at the position and it's active"() {
        given:
            toggleableWall.setActive(true)
            def levelWithActiveToggleableWall = new Level([], [toggleableWall], [], [], [], [], Mock(Player), Mock(Player), 10, 10, null, null, "src/main/resources/Levels/level2")
        expect:
            !levelWithActiveToggleableWall.isPositionFree(new Position(5, 5))
    }

    def "should allow position to be free if toggleable wall is inactive"() {
        given:
            toggleableWall.setActive(false)
            def levelWithInactiveToggleableWall = new Level([], [toggleableWall], [], [], [], [], Mock(Player), Mock(Player), 10, 10, null, null, "src/main/resources/Levels/level2")
        expect:
            levelWithInactiveToggleableWall.isPositionFree(new Position(5, 5))
    }

    def "should correctly handle player spawn position retrieval"() {
        expect:
            level.getPlayerSpawnPosition(player1) == new Position(5, 5)
            level.getPlayerSpawnPosition(player2) == new Position(6, 6)
    }

    def "should correctly set player spawn position"() {
        when:
            level.setPlayerSpawnPosition(player1, new Position(7, 7))
        then:
            level.getPlayerSpawnPosition(player1) == new Position(7, 7)
        when:
            level.setPlayerSpawnPosition(player2, new Position(20, 20))
        then:
            level.getPlayerSpawnPosition(player2) == new Position(20, 20)
    }

    def "should correctly check position for multiple obstacles (wall and toggleable wall)"() {
        given:
            toggleableWall.setActive(true)
            def levelWithWallAndActiveToggleableWall = new Level([wall], [toggleableWall], [], [], [], [], Mock(Player), Mock(Player), 10, 10, null, null, "src/main/resources/Levels/level2")
        expect:
            !levelWithWallAndActiveToggleableWall.isPositionFree(new Position(5, 5))
    }

    def "should handle toggleable wall collision only when active"() {
        given:
            wall.setPosition(new Position(10,10))
            toggleableWall.setActive(false)
            def levelWithInactiveToggleableWall = new Level([wall], [toggleableWall], [], [], [], [], Mock(Player), Mock(Player), 10, 10, null, null, "src/main/resources/Levels/level2")
        expect:
            levelWithInactiveToggleableWall.isPositionFree(new Position(5, 5))
    }

    def "should handle toggleable wall collision only if in the same position"() {
        given:
            toggleableWall.setPosition(new Position(100,100))
            def levelWithInactiveToggleableWall = new Level([], [toggleableWall], [], [], [], [], Mock(Player), Mock(Player), 10, 10, null, null, "src/main/resources/Levels/level2")
        expect:
            levelWithInactiveToggleableWall.isPositionFree(new Position(5, 5))
    }

    def "test getBackground and getLevelEndingDoor"() {
        given:
            def levelBackground = Mock(BufferedImage)
            def levelEndingDoor = new Door(null, 1, 1)
            def level = new Level([], [], [], [], [], [], player1 as Player, player2 as Player, 10, 10, levelBackground, levelEndingDoor, "nextLevel")
        expect:
            level.getBackground() == levelBackground
            level.getLevelEndingDoor() == levelEndingDoor
    }

    def "test setLevelEndingDoor and setNextLevel"() {
        given:
            def initialDoor = new Door(null, 1, 1)
            def newDoor = new Door(null, 2, 2)
            def level = new Level([], [], [], [], [], [], player1 as Player, player2 as Player, 10, 10, null, initialDoor, "nextLevel")
        when:
            level.setLevelEndingDoor(newDoor)
            level.setNextLevel("level2")
        then:
            level.getLevelEndingDoor() == newDoor
            level.getNextLevel() == "level2"
    }
}

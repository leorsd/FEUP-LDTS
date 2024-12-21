package controller.game


import game.GameManager
import model.Position
import model.elements.dynamicelements.Door
import model.elements.dynamicelements.Player
import model.elements.dynamicelements.Monster
import model.elements.staticelements.Button
import model.elements.staticelements.Key
import model.elements.staticelements.ToggleableWall
import model.elements.staticelements.Trap
import model.scenes.Level
import model.scenes.LevelLoader
import model.scenes.Menu
import spock.lang.Specification
import gui.GUI

class LevelControllerTest extends Specification {
    def level = Mock(Level)
    def player1 = Mock(Player)
    def player2 = Mock(Player)
    def monster = Mock(Monster)
    def button = Mock(Button)
    def toggleableWall = Mock(ToggleableWall)
    def key = Mock(Key)
    def trap = Mock(Trap)
    def door = Mock(Door)
    def gameManager = Mock(GameManager)
    def controller

    def setup() {
        level.getPlayer1() >> player1
        level.getPlayer2() >> player2
        level.getMonsters() >> [monster]
        level.getTraps() >> [trap]
        level.getButtons() >> [button]
        level.getToggleableWalls() >> [toggleableWall]
        level.getLevelEndingDoor() >> door

        controller = new LevelController(level)
    }

    def "checkPlayerDead should return true when player1 collides with a monster, trap, or active wall"() {
        given:
            monster.getPosition() >> new Position(10, 20)
            player1.hasCollided(monster.getPosition(), monster.getSizeX(), monster.getSizeY()) >> monsterCollision
            trap.getPosition() >> new Position(30, 40)
            player1.hasCollided(trap.getPosition(), trap.getSizeX(), trap.getSizeY()) >> trapCollision
            trap.getTarget() >> trapTarget
            player1.getName() >> "Player1"
            player1.hasCollided(toggleableWall.getPosition(), toggleableWall.getSizeX(), toggleableWall.getSizeY()) >> wallCollision
            toggleableWall.isActive() >> wallActive

        when:
            def result = controller.checkPlayerDead(player1)

        then:
            result == expectedResult

        where:
            monsterCollision | trapCollision | trapTarget | wallCollision | wallActive | expectedResult
            true             | false         | "Player1"  | false         | false      | true
            true             | true          | "Player1"  | false         | false      | true
            false            | true          | "Both"     | false         | false      | true
            false            | false         | "Player1"  | true          | true       | true
            false            | false         | "Player1"  | false         | false      | false
            false            | true          | "Player2"  | false         | false      | false
    }

    def "checkButtonsClicked should deactivate the wall when only player1 collides with the button"() {
        given:
            player1.getPosition() >> new Position(8,9)
            button.hasCollided(player1.getPosition(), player1.getSizeX(), player1.getSizeY()) >> true
            player2.getPosition() >> new Position(6,9)
            button.hasCollided(player2.getPosition(), player2.getSizeX(), player2.getSizeY()) >> false
            button.getToggleableWall() >> toggleableWall

        when:
            controller.checkButtonsClicked()

        then:
            1 * button.setPressed(true)
            1 * toggleableWall.setActive(false)
    }

    def "checkButtonsClicked should deactivate the wall when only player2 collides with the button"() {
        given:
            player1.getPosition() >> new Position(8,9)
            button.hasCollided(player1.getPosition(), player1.getSizeX(), player1.getSizeY()) >> false
            player2.getPosition() >> new Position(6,9)
            button.hasCollided(player2.getPosition(), player2.getSizeX(), player2.getSizeY()) >> true
            button.getToggleableWall() >> toggleableWall

        when:
            controller.checkButtonsClicked()

        then:
            1 * button.setPressed(true)
            1 * toggleableWall.setActive(false)
    }

    def "checkButtonsClicked should deactivate the wall when both players collide with the button"() {
        given:
            player1.getPosition() >> new Position(8,9)
            button.hasCollided(player1.getPosition(), player1.getSizeX(), player1.getSizeY()) >> true
            player2.getPosition() >> new Position(6,9)
            button.hasCollided(player2.getPosition(), player2.getSizeX(), player2.getSizeY()) >> true
            button.getToggleableWall() >> toggleableWall

        when:
            controller.checkButtonsClicked()

        then:
            1 * button.setPressed(true)
            1 * toggleableWall.setActive(false)
    }

    def "checkButtonsClicked should leave the wall active when none of the players collide with the button"() {
        given:
            player1.getPosition() >> new Position(8,9)
            button.hasCollided(player1.getPosition(), player1.getSizeX(), player1.getSizeY()) >> false
            player2.getPosition() >> new Position(6,9)
            button.hasCollided(player2.getPosition(), player2.getSizeX(), player2.getSizeY()) >> false
            button.getToggleableWall() >> toggleableWall

        when:
            controller.checkButtonsClicked()

        then:
            1 * button.setPressed(false)
            1 * toggleableWall.setActive(true)
    }

    def "key targets player 1 and player 1 collides"() {
        given:
            player1.getName() >> "Player1"
            player1.getPosition() >> new Position(5, 5)
            player2.getName() >> "Player2"
            player2.getPosition() >> new Position(8, 10)

            key.getTarget() >> "Player1"
            player1.hasCollided(key.getPosition(), key.getSizeX(), key.getSizeY()) >> true
            key.isCollected() >> false
            level.getKeys() >> [key]

        when:
            controller.collectKeys()

        then:
            1 * key.setCollected(true)
    }

    def "key targets player 1 but player 1 does not collide"() {
        given:
            player1.getName() >> "Player1"
            player1.getPosition() >> new Position(5, 5)
            player2.getName() >> "Player2"
            player2.getPosition() >> new Position(8, 10)

            key.getTarget() >> "Player1"
            player1.hasCollided(key.getPosition(), key.getSizeX(), key.getSizeY()) >> false
            key.isCollected() >> false
            level.getKeys() >> [key]

        when:
            controller.collectKeys()

        then:
            0 * key.setCollected(true)
    }

    def "key targets player 2 and player 2 collides"() {
        given:
            player1.getName() >> "Player1"
            player1.getPosition() >> new Position(5, 5)
            player2.getName() >> "Player2"
            player2.getPosition() >> new Position(8, 10)

            key.getTarget() >> "Player2"
            player2.hasCollided(key.getPosition(), key.getSizeX(), key.getSizeY()) >> true
            key.isCollected() >> false
            level.getKeys() >> [key]

        when:
            controller.collectKeys()

        then:
            1 * key.setCollected(true)
    }

    def "key targets player 2 but player 2 does not collide"() {
        given:
            player1.getName() >> "Player1"
            player1.getPosition() >> new Position(5, 5)
            player2.getName() >> "Player2"
            player2.getPosition() >> new Position(8, 10)

            key.getTarget() >> "Player2"
            player2.hasCollided(key.getPosition(), key.getSizeX(), key.getSizeY()) >> false
            key.isCollected() >> false
            level.getKeys() >> [key]

        when:
            controller.collectKeys()

        then:
            0 * key.setCollected(true)
    }

    def "key does not target either player"() {
        given:
            player1.getName() >> "Player1"
            player1.getPosition() >> new Position(5, 5)
            player2.getName() >> "Player2"
            player2.getPosition() >> new Position(8, 10)

            key.getTarget() >> "None"
            key.isCollected() >> false
            level.getKeys() >> [key]

        when:
            controller.collectKeys()

        then:
            0 * key.setCollected(true)
    }

    def "allKeysCollected returns true and opens door when all keys are collected"() {
        given:
        def key1 = Mock(Key)
        key1.isCollected() >> true
        def key2 = Mock(Key)
        key2.isCollected() >> true
        level.getKeys() >> [key1, key2]

        when:
        def result = controller.allKeysCollected()

        then:
        result == true
        1 * door.setState(Door.STATE.OPEN)
    }

    def "allKeysCollected returns false when at least one key is not collected"() {
        given:
        def key1 = Mock(Key)
        key1.isCollected() >> true
        def key2 = Mock(Key)
        key2.isCollected() >> false
        level.getKeys() >> [key1, key2]


        when:
        def result = controller.allKeysCollected()

        then:
        result == false
        0 * door.setState(Door.STATE.OPEN)
    }

    def "playerDied resets position, un-collects a key that matches and is collected, and closes the door"() {
        given:
        def spawnPosition = new Position(5, 5)
        level.getPlayerSpawnPosition(player1) >> spawnPosition
        player1.getName() >> "Player1"

        def matchingKey = Mock(Key)
        matchingKey.getPosition() >> new Position(40,60)
        matchingKey.getTarget() >> "Player1"
        matchingKey.isCollected() >> true

        level.getKeys() >> [matchingKey]

        when:
        controller.playerDied(player1)

        then:
        1 * player1.setPosition(spawnPosition)
        1 * matchingKey.setCollected(false)
        1 * door.setState(Door.STATE.CLOSED)
    }

    def "playerDied resets position, does not un-collect a key that matches but is not collected, and closes the door"() {
        given:
        def spawnPosition = new Position(5, 5)
        level.getPlayerSpawnPosition(player1) >> spawnPosition
        player1.getName() >> "Player1"

        def matchingKey = Mock(Key)
        matchingKey.getPosition() >> new Position(50,60)
        matchingKey.getTarget() >> "Player1"
        matchingKey.isCollected() >> false

        level.getKeys() >> [matchingKey]
        level.getLevelEndingDoor() >> door

        when:
        controller.playerDied(player1)

        then:
        1 * player1.setPosition(spawnPosition)
        0 * matchingKey.setCollected(_)
        1 * door.setState(Door.STATE.CLOSED)
    }

    def "playerDied resets position, does not un-collect a key that doesn't match but is not collected, and closes the door"() {
        given:
        def spawnPosition = new Position(5, 5)
        level.getPlayerSpawnPosition(player1) >> spawnPosition
        player1.getName() >> "Player1"

        def unmatchingKey = Mock(Key)
        unmatchingKey.getPosition() >> new Position(60,60)
        unmatchingKey.getTarget() >> "Player2"
        unmatchingKey.isCollected() >> true

        level.getKeys() >> [unmatchingKey]
        level.getLevelEndingDoor() >> door

        when:
        controller.playerDied(player1)

        then:
        1 * player1.setPosition(spawnPosition)
        0 * unmatchingKey.setCollected(_)
        1 * door.setState(Door.STATE.CLOSED)
    }

    def "should reset player position and keys when player dies"() {
        given:
            player1.hasCollided(_ as Position, _ as int, _ as int) >> true
            key.getTarget() >> "Tergon"
            player1.getName() >> "Tergon"
            key.isCollected() >> true
            level.getKeys() >> [key]

        and:
            def spawnPosition = new Position(5, 5)
            level.getPlayerSpawnPosition(player1) >> spawnPosition

        when:
            controller.playerDied(player1)

        then:
            1 * player1.setPosition(new Position(5, 5))
            1 * key.setCollected(false)
            1 * door.setState(Door.STATE.CLOSED)
    }

    def "should return to menu when quit action is triggered"() {
        when:
            controller.update(gameManager, [GUI.ACTION.QUIT] as Set, 0)

        then:
            1 * gameManager.setCurrentScene(_ as Menu)
    }
}


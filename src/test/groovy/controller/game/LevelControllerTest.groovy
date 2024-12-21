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
    def door = Mock(Door)
    def trap = Mock(Trap)
    def gameManager = Mock(GameManager)
    def controller

    def setup() {
        level.getPlayer1() >> player1
        level.getPlayer2() >> player2
        level.getMonsters() >> [monster]
        level.getTraps() >> [trap]
        level.getButtons() >> [button]
        level.getToggleableWalls() >> [toggleableWall]
        level.getKeys() >> [key]
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

        def "should collect keys when player collides with corresponding key"() {
            given:
                player1.getName() >> "Player1"
                player1.getPosition() >> new Position(5, 5)
                player1.getSizeX() >> 1
                player1.getSizeY() >> 1

                key.getTarget() >> "Player1"
                player1.hasCollided(_, _, _) >> true
                key.isCollected() >> false

            when:
                controller.collectKeys()

            then:
                1 * key.setCollected(true)
        }

        def "should not collect keys when player collides with a key with a different target"() {
            given:
                player1.getName() >> "Player1"
                player1.getPosition() >> new Position(5, 5)
                player1.getSizeX() >> 1
                player1.getSizeY() >> 1

                key.getTarget() >> "Player2"
                key.hasCollided(_, _, _) >> true
                key.isCollected() >> false

            when:
                controller.collectKeys()

            then:
                0 * key.setCollected(true)
        }

        def "should not collect keys when player does not collide with a corresponding key"() {
            given:
                player1.getName() >> "Player1"
                player1.getPosition() >> new Position(5, 5)
                player1.getSizeX() >> 1
                player1.getSizeY() >> 1

                key.getTarget() >> "Player1"
                key.hasCollided(_, _, _) >> false
                key.isCollected() >> false

            when:
                controller.collectKeys()

            then:
                0 * key.setCollected(true)
        }


    def "should reset player position and keys when player dies"() {
        given:
            player1.hasCollided(_, _, _) >> true
            key.getTarget() >> "Tergon"
            player1.getName() >> "Tergon"
            key.isCollected() >> true

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


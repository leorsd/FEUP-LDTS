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

    def "should collect keys when player collides with key"() {
        given: "Player1 is at the same position as a key"
        player1.getName() >> "Player1"
        player1.getPosition() >> new Position(5, 5)
        player1.getSizeX() >> 1
        player1.getSizeY() >> 1

        key.getTarget() >> "Player1"
        key.hasCollided(_, _, _) >> true
        key.isCollected() >> false

        when: "The LevelController updates key collection"
        controller.collectKeys()

        then: "The key is marked as collected"
        1 * key.setCollected(true)
    }

    def "should deactivate toggleable wall when button is pressed"() {
        given: "A button is pressed by Player1"
        player1.getPosition() >> new Position(5, 5)
        player1.getSizeX() >> 1
        player1.getSizeY() >> 1
        button.hasCollided(_, _, _) >> true

        button.getToggleableWall() >> toggleableWall

        when: "The LevelController checks for buttons clicked"
        controller.checkButtonsClicked()

        then: "The button is pressed and the wall is deactivated"
        1 * button.setPressed(true)
        1 * toggleableWall.setActive(false)
    }

    def "should reset player position and keys when player dies"() {
        given: "Player1 collides with a monster and has collected a key"
        player1.hasCollided(_, _, _) >> true
        key.getTarget() >> "Tergon"
        player1.getName() >> "Tergon"
        key.isCollected() >> true

        and: "The player's spawn position is defined"
        def spawnPosition = new Position(5, 5)
        level.getPlayerSpawnPosition(player1) >> spawnPosition

        when: "The LevelController handles player death"
        controller.playerDied(player1)

        then: "Player's position is reset and collected keys are reset"
        1 * player1.setPosition(new Position(5, 5))
        1 * key.setCollected(false)
        1 * door.setState(Door.STATE.CLOSED)
    }

    def "should return to menu when quit action is triggered"() {
        when: "The quit action is performed"
        controller.update(gameManager, [GUI.ACTION.QUIT] as Set, 0)

        then: "The current scene is set to the menu"
        1 * gameManager.setCurrentScene(_ as Menu)
    }
}


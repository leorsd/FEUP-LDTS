package controller.game

import gui.GUI
import game.GameManager
import model.elements.dynamicelements.Player
import model.Position
import model.scenes.Level
import spock.lang.Specification

class Player1ControllerTest extends Specification {
    def level
    def player
    def gameManager
    def controller

    def defaultSetup() {
        level = Mock(Level)
        player = Mock(Player)
        gameManager = Mock(GameManager)
        controller = new Player1Controller(level)
        level.getPlayer1() >> player
    }

    def defaultCleanUp() {
        level = null
        player = null
        gameManager = null
        controller = null
    }

    def "should move player 1 left if position is free"() {
        given: "Player is at position (5, 5)"
        defaultSetup()
        def currentPosition = new Position(5, 5)
        def desiredPosition = new Position(4, 5)
        player.getPosition() >> currentPosition
        player.getSizeX() >> 1
        player.getSizeY() >> 1

        and: "Position to the left is free"
        level.isPositionFree(desiredPosition) >> true

        when: "Player1Controller is commanded to move left"
        controller.movePlayer1Left()

        then: "Player's position is updated"
        1 * player.setPosition(desiredPosition)

        cleanup:
        defaultCleanUp()
    }

    def "should not move player 1 left if position is occupied"() {
        given: "Player is at position (5, 5)"
        defaultSetup()
        def currentPosition = new Position(5, 5)
        def desiredPosition = new Position(4, 5)
        player.getPosition() >> currentPosition
        player.getSizeX() >> 1
        player.getSizeY() >> 1

        and: "Position to the left is occupied"
        level.isPositionFree(desiredPosition) >> false

        when: "Player1Controller is commanded to move left"
        controller.movePlayer1Left()

        then: "Player's position is not updated"
        0 * player.setPosition(_)

        cleanup:
        defaultCleanUp()
    }

    def "should move player 1 right if position is free"() {
        given: "Player is at position (5, 5)"
        defaultSetup()
        def currentPosition = new Position(5, 5)
        def desiredPosition = new Position(6, 5)
        player.getPosition() >> currentPosition
        player.getSizeX() >> 1
        player.getSizeY() >> 1

        and: "Position to the right is free"
        level.isPositionFree(desiredPosition) >> true

        when: "Player1Controller is commanded to move right"
        controller.movePlayer1Right()

        then: "Player's position is updated"
        1 * player.setPosition(desiredPosition)

        cleanup:
        defaultCleanUp()
    }

    def "should not move player 1 down if already on ground"() {
        given: "Player is on the ground"
        defaultSetup()
        def currentPosition = new Position(5, 5)
        player.getPosition() >> currentPosition
        player.getSizeX() >> 1
        player.getSizeY() >> 1
        level.isPositionFree(_) >> false

        when: "Player1Controller tries to move down"
        controller.movePlayer1Down()

        then: "Player does not move"
        0 * player.setPosition(_)

        cleanup:
        defaultCleanUp()
    }

    def "should move player 1 based on speed in update method"() {
        given: "Player has speed 2"
        defaultSetup()
        player.getSpeed() >> 2
        def currentPosition = new Position(5, 5)
        player.getPosition() >> currentPosition
        player.getSizeX() >> 1
        player.getSizeY() >> 1

        and: "Positions are free"
        level.isPositionFree(_) >> true

        when: "Update is called with RIGHT action"
        controller.update(gameManager, [GUI.ACTION.RIGHT] as Set, 0)

        then: "Player moves 2 positions to the right"
        2 * player.setPosition(_)

        cleanup:
        defaultCleanUp()
    }

    def "can jump"() {
        given:
            defaultSetup()
            player.getSizeX() >> 10
            player.getPosition() >> new Position(0,0)
            player.getPosition().getX() >> 0
            level.isPositionFree(_) >> true
        expect:
            controller.canJump()
    }

    def "jump"() {
        given:
        defaultSetup()
        player.getSizeX() >> 10
        player.getSizeX() >> 1
        player.getSizeY() >> 1
        player.getPosition() >> new Position(0,0)
        player.getPosition().getX() >> 0
        level.isPositionFree(_) >> false
        when:
        controller.jump()
        then:
        0 * level.equals(_)
    }

    def "test orientation while jumping; previous: UPRIGHT, next: UPRIGHT"() {
        given:
        defaultSetup()
        player.getSizeX() >> 10
        player.getSizeX() >> 320
        player.getSizeY() >> 180
        player.getPosition() >> new Position(0,0)
        player.getPosition().getX() >> 0
        level.isPositionFree(_) >> true
        player.getMaxJumpHeight() >> 10
        player.getOrientation() >> Player.ORIENTATION.UPRIGHT
        player.getLastActionCount() >> 2
        def actions = [GUI.ACTION.UP, GUI.ACTION.RIGHT] as Set
        when:
        controller.setJumping(true)
        controller.update(gameManager, actions, 1000)
        then:
        1 * player.setLastActionCount(3)
    }

    def "test orientation while jumping; previous: STANDING, next: UPRIGHT"() {
        given:
            defaultSetup()
            player.getSizeX() >> 10
            player.getSizeX() >> 320
            player.getSizeY() >> 180
            player.getSpeed() >> 1
            player.getPosition() >> new Position(0,0)
            player.getPosition().getX() >> 0
            level.isPositionFree(_) >> true
            player.getMaxJumpHeight() >> 10
            player.getOrientation() >> Player.ORIENTATION.STANDING
            def actions = [GUI.ACTION.UP, GUI.ACTION.RIGHT] as Set
        when:
            controller.setJumping(true)
            controller.update(gameManager, actions, 1000)
        then:
            1 * player.setOrientation(Player.ORIENTATION.UPRIGHT)
            1 * player.setLastActionCount(0)
    }

    def "test orientation while jumping; previous: UPLEFT, next: UPLEFT"() {
        given:
        defaultSetup()
        player.getSizeX() >> 10
        player.getSizeX() >> 320
        player.getSizeY() >> 180
        player.getPosition() >> new Position(0,0)
        player.getPosition().getX() >> 0
        level.isPositionFree(_) >> true
        player.getMaxJumpHeight() >> 10
        player.getOrientation() >> Player.ORIENTATION.UPLEFT
        player.getLastActionCount() >> 2
        def actions = [GUI.ACTION.LEFT] as Set
        when:
        controller.setJumping(true)
        controller.update(gameManager, actions, 1000)
        then:
        1 * player.setLastActionCount(3)
    }

    def "test orientation while jumping; previous: STANDING, next: UPLEFT"() {
        given:
        defaultSetup()
        player.getSizeX() >> 10
        player.getSizeX() >> 320
        player.getSizeY() >> 180
        player.getPosition() >> new Position(0,0)
        player.getPosition().getX() >> 0
        level.isPositionFree(_) >> true
        player.getMaxJumpHeight() >> 10
        player.getOrientation() >> Player.ORIENTATION.STANDING
        def actions = [GUI.ACTION.LEFT] as Set
        when:
        controller.setJumping(true)
        controller.update(gameManager, actions, 1000)
        then:
        1 * player.setOrientation(Player.ORIENTATION.UPLEFT)
        1 * player.setLastActionCount(0)
    }

    def "test orientation while jumping; previous: UP, next: UP"() {
        given:
        defaultSetup()
        player.getSizeX() >> 10
        player.getSizeX() >> 320
        player.getSizeY() >> 180
        player.getPosition() >> new Position(0,0)
        player.getPosition().getX() >> 0
        level.isPositionFree(_) >> true
        player.getMaxJumpHeight() >> 10
        player.getOrientation() >> Player.ORIENTATION.UP
        player.getLastActionCount() >> 2
        def actions = [GUI.ACTION.UP] as Set
        when:
        controller.setJumping(true)
        controller.update(gameManager, actions, 1000)
        then:
        1 * player.setLastActionCount(3)
    }

    def "test orientation while jumping; previous: STANDING, next: UP"() {
        given:
        defaultSetup()
        player.getSizeX() >> 10
        player.getSizeX() >> 320
        player.getSizeY() >> 180
        player.getPosition() >> new Position(0,0)
        player.getPosition().getX() >> 0
        level.isPositionFree(_) >> true
        player.getMaxJumpHeight() >> 10
        player.getOrientation() >> Player.ORIENTATION.STANDING
        def actions = [GUI.ACTION.UP] as Set
        when:
        controller.setJumping(true)
        controller.update(gameManager, actions, 1000)
        then:
        1 * player.setOrientation(Player.ORIENTATION.UP)
        1 * player.setLastActionCount(0)
    }

    def "test orientation; previous: UPRIGHT, next: UPRIGHT"() {
        given:
        defaultSetup()
        player.getSizeX() >> 10
        player.getSizeX() >> 320
        player.getSizeY() >> 180
        player.getPosition() >> new Position(0,0)
        player.getPosition().getX() >> 0
        level.isPositionFree(_) >> true
        player.getMaxJumpHeight() >> 10
        player.getOrientation() >> Player.ORIENTATION.UPRIGHT
        player.getLastActionCount() >> 2
        def actions = [GUI.ACTION.UP,  GUI.ACTION.RIGHT] as Set
        when:
        controller.update(gameManager, actions, 1000)
        then:
        1 * player.setLastActionCount(3)
    }

    def "test orientation; previous: STANDING, next: UPRIGHT"() {
        given:
        defaultSetup()
        player.getSizeX() >> 10
        player.getSizeX() >> 320
        player.getSizeY() >> 180
        player.getPosition() >> new Position(0,0)
        player.getPosition().getX() >> 0
        level.isPositionFree(_) >> true
        player.getMaxJumpHeight() >> 10
        player.getOrientation() >> Player.ORIENTATION.STANDING
        def actions = [GUI.ACTION.UP, GUI.ACTION.RIGHT] as Set
        when:
        controller.update(gameManager, actions, 1000)
        then:
        1 * player.setOrientation(Player.ORIENTATION.UPRIGHT)
        1 * player.setLastActionCount(0)
    }

    def "test orientation; previous: UPLEFT, next: UPLEFT"() {
        given:
        defaultSetup()
        player.getSizeX() >> 10
        player.getSizeX() >> 320
        player.getSizeY() >> 180
        player.getPosition() >> new Position(0,0)
        player.getPosition().getX() >> 0
        level.isPositionFree(_) >> true
        player.getMaxJumpHeight() >> 10
        player.getOrientation() >> Player.ORIENTATION.UPLEFT
        player.getLastActionCount() >> 2
        def actions = [GUI.ACTION.UP,  GUI.ACTION.LEFT] as Set
        when:
        controller.update(gameManager, actions, 1000)
        then:
        1 * player.setLastActionCount(3)
    }

    def "test orientation previous: STANDING, next: UPRIGHT"() {
        given:
        defaultSetup()
        player.getSizeX() >> 10
        player.getSizeX() >> 320
        player.getSizeY() >> 180
        player.getPosition() >> new Position(0,0)
        player.getPosition().getX() >> 0
        level.isPositionFree(_) >> true
        player.getMaxJumpHeight() >> 10
        player.getOrientation() >> Player.ORIENTATION.STANDING
        def actions = [GUI.ACTION.UP, GUI.ACTION.LEFT] as Set
        when:
        controller.update(gameManager, actions, 1000)
        then:
        1 * player.setOrientation(Player.ORIENTATION.UPLEFT)
        1 * player.setLastActionCount(0)
    }

    def "test orientation; previous: UP, next: UP"() {
        given:
        defaultSetup()
        player.getSizeX() >> 10
        player.getSizeX() >> 320
        player.getSizeY() >> 180
        player.getPosition() >> new Position(0,0)
        player.getPosition().getX() >> 0
        level.isPositionFree(_) >> true
        player.getMaxJumpHeight() >> 10
        player.getOrientation() >> Player.ORIENTATION.UP
        player.getLastActionCount() >> 2
        def actions = [GUI.ACTION.UP] as Set
        when:
        controller.update(gameManager, actions, 1000)
        then:
        1 * player.setLastActionCount(3)
    }

    def "test orientation previous: STANDING, next: UP"() {
        given:
        defaultSetup()
        player.getSizeX() >> 10
        player.getSizeX() >> 320
        player.getSizeY() >> 180
        player.getPosition() >> new Position(0,0)
        player.getPosition().getX() >> 0
        level.isPositionFree(_) >> true
        player.getMaxJumpHeight() >> 10
        player.getOrientation() >> Player.ORIENTATION.STANDING
        def actions = [GUI.ACTION.UP] as Set
        when:
        controller.update(gameManager, actions, 1000)
        then:
        1 * player.setOrientation(Player.ORIENTATION.UP)
        1 * player.setLastActionCount(0)
    }

    def "test orientation; previous: DOWNRIGHT, next: DOWNRIGHT"() {
        given:
        defaultSetup()
        player.getSizeX() >> 10
        player.getSizeX() >> 320
        player.getSizeY() >> 180
        player.getPosition() >> new Position(0,0)
        player.getPosition().getX() >> 0
        level.isPositionFree(_) >> true
        player.getMaxJumpHeight() >> 10
        player.getOrientation() >> Player.ORIENTATION.DOWNRIGHT
        player.getLastActionCount() >> 2
        def actions = [GUI.ACTION.DOWN,  GUI.ACTION.RIGHT] as Set
        when:
        controller.update(gameManager, actions, 1000)
        then:
        1 * player.setLastActionCount(3)
    }

    def "test orientation; previous: STANDING, next: DOWNRIGHT"() {
        given:
        defaultSetup()
        player.getSizeX() >> 10
        player.getSizeX() >> 320
        player.getSizeY() >> 180
        player.getPosition() >> new Position(0,0)
        player.getPosition().getX() >> 0
        level.isPositionFree(_) >> true
        player.getMaxJumpHeight() >> 10
        player.getOrientation() >> Player.ORIENTATION.STANDING
        def actions = [GUI.ACTION.DOWN, GUI.ACTION.RIGHT] as Set
        when:
        controller.update(gameManager, actions, 1000)
        then:
        1 * player.setOrientation(Player.ORIENTATION.DOWNRIGHT)
        1 * player.setLastActionCount(0)
    }

    def "test orientation; previous: DOWNLEFT, next: DOWNLEFT"() {
        given:
        defaultSetup()
        player.getSizeX() >> 10
        player.getSizeX() >> 320
        player.getSizeY() >> 180
        player.getPosition() >> new Position(0,0)
        player.getPosition().getX() >> 0
        level.isPositionFree(_) >> true
        player.getMaxJumpHeight() >> 10
        player.getOrientation() >> Player.ORIENTATION.DOWNLEFT
        player.getLastActionCount() >> 2
        def actions = [GUI.ACTION.DOWN,  GUI.ACTION.LEFT] as Set
        when:
        controller.update(gameManager, actions, 1000)
        then:
        1 * player.setLastActionCount(3)
    }

    def "test orientation; previous: STANDING, next: DOWNLEFT"() {
        given:
        defaultSetup()
        player.getSizeX() >> 10
        player.getSpeed() >> 1
        player.getSizeX() >> 320
        player.getSizeY() >> 180
        player.getPosition() >> new Position(0,0)
        player.getPosition().getX() >> 0
        level.isPositionFree(_) >> true
        player.getMaxJumpHeight() >> 10
        player.getOrientation() >> Player.ORIENTATION.STANDING
        def actions = [GUI.ACTION.DOWN, GUI.ACTION.LEFT] as Set
        when:
        controller.update(gameManager, actions, 1000)
        then:
        1 * player.setOrientation(Player.ORIENTATION.DOWNLEFT)
        1 * player.setLastActionCount(0)
    }

    def "test orientation; previous: DOWN, next: DOWN"() {
        given:
        defaultSetup()
        player.getSizeX() >> 10
        player.getSizeX() >> 320
        player.getSizeY() >> 180
        player.getPosition() >> new Position(0,0)
        player.getPosition().getX() >> 0
        level.isPositionFree(_) >> true
        player.getMaxJumpHeight() >> 10
        player.getOrientation() >> Player.ORIENTATION.DOWN
        player.getLastActionCount() >> 2
        def actions = [GUI.ACTION.DOWN] as Set
        when:
        controller.update(gameManager, actions, 1000)
        then:
        1 * player.setLastActionCount(3)
    }

    def "test orientation; previous: STANDING, next: DOWN"() {
        given:
        defaultSetup()
        player.getSizeX() >> 10
        player.getSizeX() >> 320
        player.getSizeY() >> 180
        player.getPosition() >> new Position(0,0)
        player.getPosition().getX() >> 0
        level.isPositionFree(_) >> true
        player.getMaxJumpHeight() >> 10
        player.getOrientation() >> Player.ORIENTATION.STANDING
        def actions = [GUI.ACTION.DOWN] as Set
        when:
        controller.update(gameManager, actions, 1000)
        then:
        1 * player.setOrientation(Player.ORIENTATION.DOWN)
        1 * player.setLastActionCount(0)
    }

    def "test orientation; previous: RIGHT, next: RIGHT"() {
        given:
        defaultSetup()
        player.getSizeX() >> 10
        player.getSizeX() >> 320
        player.getSizeY() >> 180
        player.getPosition() >> new Position(0,0)
        player.getPosition().getX() >> 0
        level.isPositionFree(_) >> true
        player.getMaxJumpHeight() >> 10
        player.getOrientation() >> Player.ORIENTATION.RIGHT
        player.getLastActionCount() >> 2
        def actions = [GUI.ACTION.RIGHT] as Set
        when:
        controller.update(gameManager, actions, 1000)
        then:
        1 * player.setLastActionCount(3)
    }

    def "test orientation; previous: STANDING, next: RIGHT"() {
        given:
        defaultSetup()
        player.getSizeX() >> 10
        player.getSizeX() >> 320
        player.getSizeY() >> 180
        player.getPosition() >> new Position(0,0)
        player.getPosition().getX() >> 0
        level.isPositionFree(_) >> true
        player.getMaxJumpHeight() >> 10
        player.getOrientation() >> Player.ORIENTATION.STANDING
        def actions = [GUI.ACTION.RIGHT] as Set
        when:
        controller.update(gameManager, actions, 1000)
        then:
        1 * player.setOrientation(Player.ORIENTATION.RIGHT)
        1 * player.setLastActionCount(0)
    }

    def "test orientation; previous: LEFT, next: LEFT"() {
        given:
        defaultSetup()
        player.getSizeX() >> 10
        player.getSizeX() >> 320
        player.getSizeY() >> 180
        player.getPosition() >> new Position(0,0)
        player.getPosition().getX() >> 0
        level.isPositionFree(_) >> true
        player.getMaxJumpHeight() >> 10
        player.getOrientation() >> Player.ORIENTATION.LEFT
        player.getLastActionCount() >> 2
        def actions = [GUI.ACTION.LEFT] as Set
        when:
        controller.update(gameManager, actions, 1000)
        then:
        1 * player.setLastActionCount(3)
    }

    def "test orientation; previous: STANDING, next: LEFT"() {
        given:
        defaultSetup()
        player.getSizeX() >> 10
        player.getSizeX() >> 320
        player.getSizeY() >> 180
        player.getPosition() >> new Position(0,0)
        player.getPosition().getX() >> 0
        level.isPositionFree(_) >> true
        player.getMaxJumpHeight() >> 10
        player.getOrientation() >> Player.ORIENTATION.STANDING
        def actions = [GUI.ACTION.LEFT] as Set
        when:
        controller.update(gameManager, actions, 1000)
        then:
        1 * player.setOrientation(Player.ORIENTATION.LEFT)
        1 * player.setLastActionCount(0)
    }

    def "test orientation; previous: STANDING, next: STANDING"() {
        given:
        defaultSetup()
        player.getSizeX() >> 10
        player.getSizeX() >> 320
        player.getSizeY() >> 180
        player.getPosition() >> new Position(0,0)
        player.getPosition().getX() >> 0
        level.isPositionFree(_) >> true
        player.getMaxJumpHeight() >> 10
        player.getOrientation() >> Player.ORIENTATION.STANDING
        player.getLastActionCount() >> 2
        def actions = [] as Set
        when:
        controller.update(gameManager, actions, 1000)
        then:
        1 * player.setLastActionCount(3)
    }

    def "test orientation; previous: LEFT, next: STANDING"() {
        given:
        defaultSetup()
        player.getSizeX() >> 10
        player.getSizeX() >> 320
        player.getSizeY() >> 180
        player.getPosition() >> new Position(0,0)
        player.getPosition().getX() >> 0
        level.isPositionFree(_) >> true
        player.getMaxJumpHeight() >> 10
        player.getOrientation() >> Player.ORIENTATION.LEFT
        def actions = [] as Set
        when:
        controller.update(gameManager, actions, 1000)
        then:
        1 * player.setOrientation(Player.ORIENTATION.STANDING)
        1 * player.setLastActionCount(0)
    }

    def "test while jumping cant jump anymore"() {
        given:
        defaultSetup()
        player.getSizeX() >> 10
        player.getSizeX() >> 320
        player.getSizeY() >> 180
        player.getSpeed() >> 1
        player.getPosition() >> new Position(0,0)
        player.getPosition().getX() >> 0
        level.isPositionFree(_) >> false
        player.getMaxJumpHeight() >> 10
        player.getOrientation() >> Player.ORIENTATION.STANDING
        def actions = [] as Set
        when:
        controller.setJumping(true)
        controller.update(gameManager, actions, 1000)
        then:
        0 * player.setOrientation(Player.ORIENTATION.UPRIGHT)
    }
}

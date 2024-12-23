package controller.game

import gui.GUI
import game.GameManager
import model.elements.dynamicelements.Player
import model.Position
import model.scenes.Level
import spock.lang.Specification

class Player2ControllerTest extends Specification {
    def level
    def player
    def gameManager
    def controller

    def defaultSetup() {
        level = Mock(Level)
        player = Mock(Player)
        gameManager = Mock(GameManager)
        controller = new Player2Controller(level)
        level.getPlayer2() >> player
    }

    def defaultCleanUp() {
        level = null
        player = null
        gameManager = null
        controller = null
    }

    def "should move player 2 left if position is free"() {
        given:
        defaultSetup()
        def currentPosition = new Position(5, 5)
        def desiredPosition = new Position(4, 5)
        player.getPosition() >> currentPosition
        player.getSizeX() >> 1
        player.getSizeY() >> 1
        and:
        level.isPositionFree(desiredPosition) >> true
        when:
        controller.movePlayer2Left()
        then:
        1 * player.setPosition(desiredPosition)
        cleanup:
        defaultCleanUp()
    }

    def "should not move player 2 left if position is occupied"() {
        given:
        defaultSetup()
        def currentPosition = new Position(5, 5)
        def desiredPosition = new Position(4, 5)
        player.getPosition() >> currentPosition
        player.getSizeX() >> 1
        player.getSizeY() >> 1
        and:
        level.isPositionFree(desiredPosition) >> false
        when:
        controller.movePlayer2Left()
        then:
        0 * player.setPosition(_)
        cleanup:
        defaultCleanUp()
    }

    def "should move player 2 right if position is free"() {
        given:
        defaultSetup()
        def currentPosition = new Position(5, 5)
        def desiredPosition = new Position(6, 5)
        player.getPosition() >> currentPosition
        player.getSizeX() >> 1
        player.getSizeY() >> 1
        and:
        level.isPositionFree(desiredPosition) >> true
        when:
        controller.movePlayer2Right()
        then:
        1 * player.setPosition(desiredPosition)
        cleanup:
        defaultCleanUp()
    }


    def "should move player 2 up if position is free"() {
        given:
        defaultSetup()
        def currentPosition = new Position(5, 5)
        def desiredPosition = new Position(5, 4)
        player.getPosition() >> currentPosition
        player.getSizeX() >> 1
        player.getSizeY() >> 1
        and:
        level.isPositionFree(desiredPosition) >> true
        when:
        controller.movePlayer2Up()
        then:
        1 * player.setPosition(desiredPosition)
        cleanup:
        defaultCleanUp()
    }

    def "should move player 2 down if position is free"() {
        given:
        defaultSetup()
        def currentPosition = new Position(5, 5)
        def desiredPosition = new Position(5, 6)
        player.getPosition() >> currentPosition
        player.getSizeX() >> 1
        player.getSizeY() >> 1

        and:
        level.isPositionFree(desiredPosition) >> true

        when:
        controller.movePlayer2Down()

        then:
        1 * player.setPosition(desiredPosition)
        cleanup:
        defaultCleanUp()
    }

    def "should not move player 2 down if already on ground"() {
        given:
        defaultSetup()
        def currentPosition = new Position(5, 5)
        player.getPosition() >> currentPosition
        player.getSizeX() >> 1
        player.getSizeY() >> 1
        level.isPositionFree(_) >> false
        when:
        controller.movePlayer2Down()
        then:
        0 * player.setPosition(_)

        cleanup:
        defaultCleanUp()
    }

    def "should move player 2 based on speed in update method"() {
        given:
        defaultSetup()
        player.getSpeed() >> 2
        def currentPosition = new Position(5, 5)
        player.getPosition() >> currentPosition
        player.getSizeX() >> 1
        player.getSizeY() >> 1

        and:
        level.isPositionFree(_) >> true

        when:
        controller.update(gameManager, [GUI.ACTION.D] as Set, 0)

        then:
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
        def actions = [GUI.ACTION.W, GUI.ACTION.D] as Set
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
        def actions = [GUI.ACTION.W, GUI.ACTION.D] as Set
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
        def actions = [GUI.ACTION.A] as Set
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
        def actions = [GUI.ACTION.A] as Set
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
        def actions = [GUI.ACTION.W] as Set
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
        def actions = [GUI.ACTION.W] as Set
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
        def actions = [GUI.ACTION.W,  GUI.ACTION.D] as Set
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
        def actions = [GUI.ACTION.W, GUI.ACTION.D] as Set
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
        def actions = [GUI.ACTION.W,  GUI.ACTION.A] as Set
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
        def actions = [GUI.ACTION.W, GUI.ACTION.A] as Set
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
        def actions = [GUI.ACTION.W] as Set
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
        def actions = [GUI.ACTION.W] as Set
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
        def actions = [GUI.ACTION.S,  GUI.ACTION.D] as Set
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
        def actions = [GUI.ACTION.S, GUI.ACTION.D] as Set
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
        def actions = [GUI.ACTION.S,  GUI.ACTION.A] as Set
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
        def actions = [GUI.ACTION.S, GUI.ACTION.A] as Set
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
        def actions = [GUI.ACTION.S] as Set
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
        def actions = [GUI.ACTION.S] as Set
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
        def actions = [GUI.ACTION.D] as Set
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
        def actions = [GUI.ACTION.D] as Set
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
        def actions = [GUI.ACTION.A] as Set
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
        def actions = [GUI.ACTION.A] as Set
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
    def "test movePlayer with free position"() {
        given:
        defaultSetup()
        def initialPosition = new Position(5, 5)
        def targetPosition = new Position(6, 5)

        and:
        player.getPosition() >> initialPosition
        player.getSizeX() >> 2
        player.getSizeY() >> 2

        and:
        level.isPositionFree(new Position(6, 5)) >> true
        level.isPositionFree(new Position(6,6))  >> true
        level.isPositionFree(new Position(7,6))  >> true
        level.isPositionFree(new Position(7,5))  >> true
        when:
        controller.movePlayer(targetPosition)
        then:
        1 * player.setPosition(targetPosition)

        cleanup:
        defaultCleanUp()
    }

    def "test isOnGround"() {
        given:
        defaultSetup()
        def initialPosition = new Position(2, 2)
        and:
        player.getPosition() >> initialPosition
        player.getSizeX() >> 2
        player.getSizeY() >> 1
        and:
        level.isPositionFree(new Position(2,3)) >> false
        level.isPositionFree(new Position(1,3)) >> true
        level.isPositionFree(new Position(2,1)) >> true
        level.isPositionFree(new Position(3,1)) >> true
        expect:
        controller.isOnGround()
        when:
        controller.jump()
        then:
        controller.getIsJumping()
        controller.getCurrentJumpHeight() == 0
        cleanup:
        defaultCleanUp()
    }

    def "test canJump"() {
        given:
        defaultSetup()
        def initialPosition = new Position(2, 2)
        player.getPosition() >> initialPosition
        player.getSizeX() >> 2
        player.getSizeY() >> 2
        and:
        level.isPositionFree(new Position(2, 1)) >> true
        level.isPositionFree(new Position(3, 1)) >> true
        expect:
        controller.canJump()
        cleanup:
        defaultCleanUp()
    }

    def "test canJump but negative case"() {
        given:
        defaultSetup()
        def initialPosition = new Position(2, 2)
        player.getPosition() >> initialPosition
        player.getSizeX() >> 2
        player.getSizeY() >> 2
        and:
        level.isPositionFree(new Position(2, 1)) >> false
        level.isPositionFree(new Position(3, 1)) >> false
        expect:
        !controller.canJump()
        cleanup:
        defaultCleanUp()
    }

    def "test getters"() {
        given:
        defaultSetup()
        when:
        controller.setCurrentJumpHeight(10)
        controller.setJumping(false)
        then:
        controller.getCurrentJumpHeight() == 10
        controller.getIsJumping() == false
        cleanup:
        defaultCleanUp()
    }

    def "test movePlayer with speed"() {
        given:
        defaultSetup()
        def initialPosition = new Position(10, 10)
        player.getPosition() >> initialPosition
        player.getSizeX() >> 1
        player.getSizeY() >> 1
        player.getSpeed() >> 3
        and:
        def actions = [GUI.ACTION.S,GUI.ACTION.D,GUI.ACTION.A] as Set
        level.isPositionFree(_) >> true
        when:
        controller.update(gameManager, actions, 1000)
        then:
        9 * player.setPosition(_)
        cleanup:
        defaultCleanUp()
    }

    def "test jump with action up"() {
        given:
        defaultSetup()
        def initialPosition = new Position(2, 2)
        and:
        player.getPosition() >> initialPosition
        player.getSizeX() >> 2
        player.getSizeY() >> 1
        and:
        level.isPositionFree(new Position(2,3)) >> false
        level.isPositionFree(new Position(1,3)) >> true
        level.isPositionFree(new Position(2,1)) >> true
        level.isPositionFree(new Position(3,1)) >> true
        when:
        controller.update(gameManager,[GUI.ACTION.W] as Set,0)
        then:
        controller.getIsJumping()
        controller.getCurrentJumpHeight() == 0
        cleanup:
        defaultCleanUp()
    }

    def "test jump 2 times with action up"() {
        given:
        defaultSetup()
        def initialPosition = new Position(2, 6)
        and:
        player.getPosition() >> initialPosition
        player.getSizeX() >> 1
        player.getSizeY() >> 1
        player.getMaxJumpHeight() >> 2
        player.getSpeed() >> 2
        and:
        def actions = [GUI.ACTION.W] as Set
        level.isPositionFree(new Position(2,7)) >> false
        level.isPositionFree(new Position(2,5)) >> true
        when:
        controller.update(gameManager, actions, 1000)
        then:
        2 * player.setPosition(_)
        controller.getCurrentJumpHeight() == 2
        cleanup:
        defaultCleanUp()
    }

    def "test jump 2 times with action up but hit max height"() {
        given:
        defaultSetup()
        def initialPosition = new Position(2, 6)
        and:
        player.getPosition() >> initialPosition
        player.getSizeX() >> 1
        player.getSizeY() >> 1
        player.getMaxJumpHeight() >> 2
        player.getSpeed() >> 4
        and:
        def actions = [GUI.ACTION.W] as Set
        level.isPositionFree(new Position(2,7)) >> false
        level.isPositionFree(new Position(2,5)) >> true
        when:
        controller.update(gameManager, actions, 1000)
        then:
        2 * player.setPosition(_)
        controller.getCurrentJumpHeight() == 2
        cleanup:
        defaultCleanUp()
    }

    def "test jump but it cant jump"() {
        given:
        defaultSetup()
        def initialPosition = new Position(2, 6)
        and:
        player.getPosition() >> initialPosition
        player.getSizeX() >> 1
        player.getSizeY() >> 1
        player.getMaxJumpHeight() >> 4
        player.getSpeed() >> 4
        and:
        def actions = [GUI.ACTION.W] as Set
        level.isPositionFree(new Position(2,7)) >> false
        level.isPositionFree(new Position(2,5)) >> false
        when:
        controller.update(gameManager, actions, 1000)
        then:
        0 * player.setPosition(_)
        controller.getCurrentJumpHeight() == 0
        cleanup:
        defaultCleanUp()
    }
}

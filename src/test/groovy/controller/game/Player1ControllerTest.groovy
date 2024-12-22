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
}

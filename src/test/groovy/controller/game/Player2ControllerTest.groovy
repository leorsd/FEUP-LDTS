package controller.game

import gui.GUI
import game.GameManager
import model.elements.dynamicelements.Player
import model.Position
import model.scenes.Level
import spock.lang.Specification

class Player2ControllerTest extends Specification {
    def level = Mock(Level)
    def player = Mock(Player)
    def gameManager = Mock(GameManager)
    def controller

    def setup() {
        controller = new Player2Controller(level)
        level.getPlayer2() >> player
    }

    def "should move player 2 left if position is free"() {
        given: "Player2 is at position (5, 5)"
        def currentPosition = new Position(5, 5)
        def desiredPosition = new Position(4, 5)
        player.getPosition() >> currentPosition
        player.getSizeX() >> 1
        player.getSizeY() >> 1

        and: "Position to the left is free"
        level.isPositionFree(desiredPosition) >> true

        when: "Player2Controller is commanded to move left"
        controller.movePlayer2Left()

        then: "Player2's position is updated"
        1 * player.setPosition(desiredPosition)
    }

    def "should not move player 2 left if position is occupied"() {
        given: "Player2 is at position (5, 5)"
        def currentPosition = new Position(5, 5)
        def desiredPosition = new Position(4, 5)
        player.getPosition() >> currentPosition
        player.getSizeX() >> 1
        player.getSizeY() >> 1

        and: "Position to the left is occupied"
        level.isPositionFree(desiredPosition) >> false

        when: "Player2Controller is commanded to move left"
        controller.movePlayer2Left()

        then: "Player2's position is not updated"
        0 * player.setPosition(_)
    }

    def "should move player 2 right if position is free"() {
        given: "Player2 is at position (5, 5)"
        def currentPosition = new Position(5, 5)
        def desiredPosition = new Position(6, 5)
        player.getPosition() >> currentPosition
        player.getSizeX() >> 1
        player.getSizeY() >> 1

        and: "Position to the right is free"
        level.isPositionFree(desiredPosition) >> true

        when: "Player2Controller is commanded to move right"
        controller.movePlayer2Right()

        then: "Player2's position is updated"
        1 * player.setPosition(desiredPosition)
    }

    def "should not move player 2 down if already on ground"() {
        given: "Player2 is on the ground"
        def currentPosition = new Position(5, 5)
        player.getPosition() >> currentPosition
        player.getSizeX() >> 1
        player.getSizeY() >> 1
        level.isPositionFree(_) >> false

        when: "Player2Controller tries to move down"
        controller.movePlayer2Down()

        then: "Player2 does not move"
        0 * player.setPosition(_)
    }

    def "should move player 2 based on speed in update method"() {
        given: "Player2 has speed 2"
        player.getSpeed() >> 2
        def currentPosition = new Position(5, 5)
        player.getPosition() >> currentPosition
        player.getSizeX() >> 1
        player.getSizeY() >> 1

        and: "Positions are free"
        level.isPositionFree(_) >> true

        when: "Update is called with RIGHT action"
        controller.update(gameManager, [GUI.ACTION.D] as Set, 0)

        then: "Player2 moves 2 positions to the right"
        2 * player.setPosition(_)
    }
}

package controller.game

import gui.GUI
import game.GameManager
import model.elements.dynamicelements.Player
import model.Position
import model.scenes.Level
import spock.lang.Specification

class Player1ControllerTest extends Specification {
    def level = Mock(Level)
    def player1Controller = new Player1Controller(level)

    def "should move player 1 left if position is free" () {
        given: "a Level with Player 1"
        def player1 = Mock (Player)
        def position = Mock(Position)
        def desiredPosition = new Position(4,5)

        level.getPlayer1() >> player1
        player1.getPosition() >> position
        player1.getSizeX() >> 1
        player1.getSizeY() >> 1
        position.getX() >> 5
        position.getY() >> 5

        level.isPositionFree(desiredPosition) >> true

        when: "the player is commanded to move left"
        player1Controller.movePlayer1Left()

        then: "player moves 1 position to the left"
        1 * player1.setPosition(desiredPosition)
    }

    def "should not move player 1 left if position isn't free" () {
        given: "a Level with Player 1, desired position is occupied"
        def player1 = Mock (Player)
        def position = Mock(Position)
        def desiredPosition = new Position(5,5)

        level.getPlayer1() >> player1
        player1.getPosition() >> position
        player1.getSizeX() >> 1
        player1.getSizeY() >> 1
        position.getX() >> 5
        position.getY() >> 5

        level.isPositionFree(desiredPosition) >> false

        when: "the player is commanded to move left"
        player1Controller.movePlayer1Left()

        then: "player can't move to the left"
        0 * player1.setPosition(desiredPosition)
    }

    def "should move player 1 right if position is free" () {
        given: "a Level with Player 1"
        def player1 = Mock (Player)
        def position = Mock(Position)
        def desiredPosition = new Position(6,5)

        level.getPlayer1() >> player1
        player1.getPosition() >> position
        player1.getSizeX() >> 1
        player1.getSizeY() >> 1
        position.getX() >> 5
        position.getY() >> 5

        level.isPositionFree(desiredPosition) >> true

        when: "the player is commanded to move right"
        player1Controller.movePlayer1Right()

        then: "player moves 1 position to the right"
        1 * player1.setPosition(desiredPosition)
    }

    def "should not move player 1 right if position isn't free" () {
        given: "a Level with Player 1, desired position is occupied"
        def player1 = Mock (Player)
        def position = Mock(Position)
        def desiredPosition = new Position(6,5)

        level.getPlayer1() >> player1
        player1.getPosition() >> position
        player1.getSizeX() >> 1
        player1.getSizeY() >> 1
        position.getX() >> 5
        position.getY() >> 5

        level.isPositionFree(desiredPosition) >> false

        when: "the player needs to move right"
        player1Controller.movePlayer1Right()

        then: "player can't move to the right"
        0 * player1.setPosition(desiredPosition)
    }

    def "should move player 1 up if position is free" () {
        given: "a Level with Player 1"
        def player1 = Mock (Player)
        def position = Mock(Position)
        def desiredPosition = new Position(5,4)

        level.getPlayer1() >> player1
        player1.getPosition() >> position
        player1.getSizeX() >> 1
        player1.getSizeY() >> 1
        position.getX() >> 5
        position.getY() >> 5

        level.isPositionFree(desiredPosition) >> true

        when: "the player is commanded to move up"
        player1Controller.movePlayer1Up()

        then: "player moves 1 position upwards"
        1 * player1.setPosition(desiredPosition)
    }

    def "should not move player 1 up if position isn't free" () {
        given: "a Level with Player 1, desired position is occupied"
        def player1 = Mock (Player)
        def position = Mock(Position)
        def desiredPosition = new Position(5,4)

        level.getPlayer1() >> player1
        player1.getPosition() >> position
        player1.getSizeX() >> 1
        player1.getSizeY() >> 1
        position.getX() >> 5
        position.getY() >> 5

        level.isPositionFree(desiredPosition) >> false

        when: "the player needs to move up"
        player1Controller.movePlayer1Up()

        then: "player can't move upwards"
        0 * player1.setPosition(desiredPosition)
    }

    def "should move player 1 down if position is free" () {
        given: "a Level with Player 1"
        def player1 = Mock (Player)
        def position = Mock(Position)
        def desiredPosition = new Position(5,6)

        level.getPlayer1() >> player1
        player1.getPosition() >> position
        player1.getSizeX() >> 1
        player1.getSizeY() >> 1
        position.getX() >> 5
        position.getY() >> 5

        level.isPositionFree(desiredPosition) >> true

        when: "the player is commanded to move down"
        player1Controller.movePlayer1Down()

        then: "player moves 1 position downwards"
        1 * player1.setPosition(desiredPosition)
    }

    def "should not move player 1 down if position isn't free" () {
        given: "a Level with Player 1, desired position is occupied"
        def player1 = Mock (Player)
        def position = Mock(Position)
        def desiredPosition = new Position(5,6)

        level.getPlayer1() >> player1
        player1.getPosition() >> position
        player1.getSizeX() >> 1
        player1.getSizeY() >> 1
        position.getX() >> 5
        position.getY() >> 5

        level.isPositionFree(desiredPosition) >> false

        when: "the player needs to move downwards"
        player1Controller.movePlayer1Up()

        then: "player can't move downwards"
        0 * player1.setPosition(desiredPosition)
    }

    def "player1 should be commanded to move down when DOWN action is received"() {
        given: "an DOWN action"
        def player1Controller = Spy(Player1Controller, constructorArgs: [level])
        def actions = [GUI.ACTION.DOWN] as Set
        def gameManager = Mock(GameManager)
        def player1 = Mock(Player)
        def position = new Position(1,1)

        level.getPlayer1() >> player1
        player1.getPosition() >> position
        player1.getSpeed() >> 2

        when: "level is updated"
        player1Controller.update(gameManager, actions, 1)

        then: "player is commanded to move up"
        2 * player1Controller.movePlayer1Down()
    }

    def "player1 should be commanded to move to the right when RIGHT action is received"() {
        given: "an DOWN action"
        def player1Controller = Spy(Player1Controller, constructorArgs: [level])
        def actions = [GUI.ACTION.RIGHT] as Set
        def gameManager = Mock(GameManager)
        def player1 = Mock(Player)
        def position = new Position(1,1)

        level.getPlayer1() >> player1
        player1.getPosition() >> position
        player1.getSpeed() >> 2

        when: "level is updated"
        player1Controller.update(gameManager, actions, 1)

        then: "player is commanded to move to the right"
        2 * player1Controller.movePlayer1Right()
    }

    def "player1 should be commanded to move to the left when LEFT action is received"() {
        given: "an DOWN action"
        def player1Controller = Spy(Player1Controller, constructorArgs: [level])
        def actions = [GUI.ACTION.LEFT] as Set
        def gameManager = Mock(GameManager)
        def player1 = Mock(Player)
        def position = new Position(1,1)


        level.getPlayer1() >> player1
        player1.getPosition() >> position
        player1.getSpeed() >> 2

        when: "level is updated"
        player1Controller.update(gameManager, actions, 1)

        then: "player is commanded to move to the left"
        2 * player1Controller.movePlayer1Left()
    }


}

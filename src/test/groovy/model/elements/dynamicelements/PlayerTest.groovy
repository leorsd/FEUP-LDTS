package model.elements.dynamicelements

import spock.lang.Specification
import model.Position

class PlayerTest extends Specification {

    def "test hashcode"() {
        given:
            def player1 = new Player("John", 10, 10, new Position(5, 5), 10, 15)
            def player2 = new Player("John", 10, 10, new Position(5, 5), 10, 15)
            def player3 = new Player("Doe", 10, 10, new Position(5, 5), 10, 15)
        expect:
            player1.hashCode() == player2.hashCode()
            player2.hashCode() != player3.hashCode()
    }

    def "should correctly compare players for equality"() {
        given:
            def player1 = new Player("John", 10, 10, new Position(1, 1), 10, 15)
            def player2 = new Player("John", 10, 10, new Position(1, 1), 10, 15)
            def player3 = new Player("Jane", 10, 10, new Position(1, 1), 10, 15)
            def player4 = new Player("John", 10, 10, new Position(2, 1), 10, 15)
            def player5 = new Player("John", 10, 10, new Position(1, 1), 12, 15)
            def player6 = new Player("John", 10, 10, new Position(1, 1), 10, 20)

            player1.setOrientation(Player.ORIENTATION.UP)
            player2.setOrientation(Player.ORIENTATION.UP)
            player3.setOrientation(Player.ORIENTATION.DOWN)
            player4.setOrientation(Player.ORIENTATION.UP)
            player5.setOrientation(Player.ORIENTATION.UP)
            player6.setOrientation(Player.ORIENTATION.UP)

        expect:
            player1.equals(player1)
            player1 == player2
            !player1.equals(20)
            player1 != player4
            player1 != player3
            player1 != player5
            player1 != player6

        when:
            player2.setOrientation(Player.ORIENTATION.DOWN)
        then:
            player1 != player2
        when:
            player2.setOrientation(Player.ORIENTATION.STANDING)
            player2.setLastActionCount(10)
        then:
            player1 != player2
    }

    def "should correctly initialize player with constructor"() {
        given: "A new Player object"
        def position = new Position(5, 10)
        def player = new Player("Player1", 50, 60, position, 10, 15)

        expect: "The player's properties should be initialized correctly"
        player.getName() == "Player1"
        player.getSpeed() == 10
        player.getMaxJumpHeight() == 15
        player.getOrientation() == Player.ORIENTATION.STANDING
        player.getPosition() == position
        player.getSizeX() == 50
        player.getSizeY() == 60
    }

    def "should update player orientation"() {
        given: "A player with an initial orientation"
        def player = new Player("Player1", 50, 60, new Position(5, 10), 10, 15)

        when: "The player orientation is set"
        player.setOrientation(Player.ORIENTATION.UPRIGHT)

        then: "The player's orientation should be updated"
        player.getOrientation() == Player.ORIENTATION.UPRIGHT
    }

    def "should update player speed"() {
        given: "A player with an initial speed"
        def player = new Player("Player1", 50, 60, new Position(5, 10), 10, 15)

        when: "The player speed is set"
        player.setSpeed(20)

        then: "The player's speed should be updated"
        player.getSpeed() == 20
    }

    def "should update player max jump height"() {
        given: "A player with an initial max jump height"
        def player = new Player("Player1", 50, 60, new Position(5, 10), 10, 15)

        when: "The player max jump height is set"
        player.setMaxJumpHeight(25)

        then: "The player's max jump height should be updated"
        player.getMaxJumpHeight() == 25
    }

    def "should update player last action count"() {
        given: "A player with an initial last action count"
        def player = new Player("Player1", 50, 60, new Position(5, 10), 10, 15)

        when: "The player's last action count is set"
        player.setLastActionCount(5)

        then: "The player's last action count should be updated"
        player.getLastActionCount() == 5
    }

    def "should update player name"() {
        given: "A player with an initial name"
        def player = new Player("Player1", 50, 60, new Position(5, 10), 10, 15)

        when: "The player's name is set"
        player.setName("Player2")

        then: "The player's name should be updated"
        player.getName() == "Player2"
    }
}

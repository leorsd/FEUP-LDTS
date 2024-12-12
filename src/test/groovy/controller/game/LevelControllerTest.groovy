package controller.game

import gui.GUI
import game.GameManager
import model.Position
import model.scenes.Menu
import spock.lang.Specification
import model.elements.movingelements.Monster
import model.elements.movingelements.Player
import model.elements.Trap
import model.scenes.Level


class LevelControllerTest extends Specification {
    def level = Mock(Level)
    def levelController = new LevelController(level)
    def gameManager = Mock(GameManager)

    def "checkPlayer1Dead should return true if player1 collides with a monster"() {
        given: "a game with player1 and a monster"
        def player1 = Mock(Player)
        def monster = Mock(Monster)

        level.getPlayer1() >> player1
        level.getMonsters() >> [monster]
        level.getTraps() >> []

        and: "player1 collides with the monster"
        monster.getPosition() >> new Position (1,1)
        monster.getSizeX() >> 1
        monster.getSizeY() >> 1
        player1.hasCollided(monster.getPosition(), monster.getSizeX(), monster.getSizeY()) >> true

        when: "the method is invoked"
        def result = levelController.checkPlayerDead(player1)

        then: "a collision is detected, and result is true"
        result
    }

    def "checkPlayer1Dead should return true if player1 collides with a trap"() {
        given: "a game with player1 and a trap"
        def player1 = Mock(Player)
        def trap = Mock(Trap)

        level.getPlayer1() >> player1
        level.getTraps() >> [trap]
        level.getMonsters() >> []

        and: "player1 collides with the trap"
        trap.getPosition() >> new Position (1,1)
        trap.getSizeX() >> 1
        trap.getSizeY() >> 1
        trap.getTarget() >> "Lavena"
        player1.getName() >> "Lavena"
        player1.hasCollided(trap.getPosition(), trap.getSizeX(), trap.getSizeY()) >> true
        player1.getName().equals(trap.getTarget()) >> true

        when: "the method is invoked"
        def result = levelController.checkPlayerDead(player1);

        then: "a collision is detected, and result is true"
        result
    }

    def "checkPlayer2Dead should return true if player2 collides with a monster"() {
        given: "a game with players and a monster"
        def player2 = Mock(Player)
        def monster = Mock(Monster)

        level.getPlayer2() >> player2
        level.getMonsters() >> [monster]
        level.getTraps() >> []

        and: "player2 collides with the monster"
        monster.getPosition() >> new Position (1,1)
        monster.getSizeX() >> 1
        monster.getSizeY() >> 1
        player2.hasCollided(monster.getPosition(), monster.getSizeX(), monster.getSizeY()) >> true

        when: "the method is invoked"
        def result = levelController.checkPlayerDead(player2)

        then: "a collision is detected, and result is true"
        result
    }

    def "checkPlayer1Dead should return true if player2 collides with a trap"() {
        given: "a game with a player 2 and a trap"
        def player2 = Mock(Player)
        def trap = Mock(Trap)

        level.getPlayer2() >> player2
        level.getTraps() >> [trap]
        level.getMonsters() >> []

        and: "player2 collides with the trap"
        trap.getPosition() >> new Position (1,1)
        trap.getSizeX() >> 1
        trap.getSizeY() >> 1
        trap.getTarget() >> "Lavena"
        player2.getName() >> "Lavena"
        player2.hasCollided(trap.getPosition(), trap.getSizeX(), trap.getSizeY()) >> true
        player2.getName().equals(trap.getTarget()) >> true

        when: "the method is invoked"
        def result = levelController.checkPlayerDead(player2);

        then: "a collision is detected, and result is true"
        result
    }

    def "game should transition to Menu when QUIT action is received"() {
        given: "a QUIT action"
        def actions = [GUI.ACTION.QUIT] as Set

        when: "the game updates"
        levelController.update(gameManager, actions, 0)

        then: "game transitions to menu"
        1 * gameManager.setCurrentScene(_ as Menu)
        0 * _
    }
    // to be continued
}

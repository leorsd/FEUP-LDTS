package ControllerTest

import Model.Position
import controller.game.LevelController;
import spock.lang.Specification;
import Game.GameManager;
import Model.Elements.Characters.Monster;
import Model.Elements.Characters.Player;
import Model.Elements.Trap;
import Model.Scenes.Level;
import Model.Scenes.Menu;
import controller.Controller;
import GUI.GUI;


public class LevelControllerTest extends Specification {
    def level = Mock(Level)
    def levelController = new LevelController(level)

    def "checkDeadPlayers should return true if a player collides with a monster"() {
        given: "a game with players and a monster"
        def player1 = Mock(Player)
        def player2 = Mock(Player)
        def monster = Mock(Monster)

        level.getPlayer1() >> player1
        level.getPlayer2() >> player2
        level.getMonsters() >> [monster]
        level.getTraps() >> []

        and: "player1 collides with the monster"
        monster.getPosition() >> new Position (1,1)
        monster.getSizeX() >> 1
        monster.getSizeY() >> 1
        player1.hasCollided(monster.getPosition(), monster.getSizeX(), monster.getSizeY()) >> true
        player2.hasCollided(_,_,_) >> false;

        when: "The method is invoked"
        def result = levelController.checkDeadPlayers(null)

        then: "A collision is detected, and result is true"
        result
    }

    def "checkDeadPlayers should return true if a player collides with a trap"() {
        given: "a game with players and a trap"
        def player1 = Mock(Player)
        def player2 = Mock(Player)
        def trap = Mock(Trap)

        level.getPlayer1() >> player1
        level.getPlayer2() >> player2
        level.getTraps() >> [trap]
        level.getMonsters() >> []

        and: "player1 collides with the monster"
        trap.getPosition() >> new Position (1,1)
        trap.getSizeX() >> 1
        trap.getSizeY() >> 1
        trap.getTarget() >> "Lavena"
        player1.getName() >> "Lavena"
        player1.hasCollided(trap.getPosition(), trap.getSizeX(), trap.getSizeY()) >> true;
        player1.getName().equals(trap.getTarget()) >> true;
        player2.hasCollided(_,_,_) >> false;

        when: "The method is invoked"
        def result = levelController.checkDeadPlayers(null)

        then: "A collision is detected, and result is true"
        result
    }



}

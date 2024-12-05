package ControllerTest

import Game.GameManager
import Model.Scenes.Level
import Model.Elements.MovingElements.Monster
import Model.Position
import controller.game.MonsterController
import spock.lang.Specification

class MonsterControllerTest extends Specification {
    def level = Mock(Level)
    def monsterController = new MonsterController(level)
    def monster = Mock(Monster)

    def "ensure monster moves correctly" () {
        given: "a monster in a certain position"
        def freePosition = Mock (Position)

        level.isPositionFree(freePosition) >> true

        when: "the monster moves"
        monsterController.moveMonster(monster, freePosition)

        then: "the monster position is updated"
        1 * monster.setPosition(freePosition)
    }

    def "should not update monsters if time interval is too short"() {
        given: "a MonsterController and Level with monsters"
        def gameManager = Mock(GameManager)
        def position = Mock(Position)

        level.getMonsters() >> [monster]
        monster.getPosition() >> position
        position.getX() >> 5

        monsterController.getLastUpdateTime() >> 1000

        when: "update is called with insufficient time passed"
        monsterController.update(gameManager, [] as Set, 1100)

        then: "no monsters are moved"
        0 * monster.setPosition(_)
    }

    def "should update monsters after sufficient time has passed"() {
        given: "a MonsterController and Level with monsters"
        def gameManager = Mock(GameManager)
        def position = Mock(Position)

        level.getMonsters() >> [monster]
        monster.getPosition() >> position
        position.getX() >> 5

        level.isPositionFree(position) >> true
        monsterController.getLastUpdateTime() >> 1000

        when: "update is called with sufficient time passed"
        monsterController.update((gameManager), [] as Set, 1300)

        then: "monsters are moved to the desired position"
        1 * monster.setPosition(_)
    }
}

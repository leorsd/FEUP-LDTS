package controller.game


import model.scenes.Level
import model.elements.dynamicelements.Monster
import model.Position
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
}

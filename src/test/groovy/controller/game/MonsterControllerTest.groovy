package controller.game
import game.GameManager
import model.Position
import model.elements.dynamicelements.Monster
import model.scenes.Level
import spock.lang.Specification

class MonsterControllerTest extends Specification {
    def level = Mock(Level)
    def monster = Mock(Monster)
    def controller

    def setup() {
        level = Mock(Level)
        monster = Mock(Monster)
        controller = new MonsterController(level)
    }

    def "should move monster and change direction when reaching boundaries"() {
        given: "A monster at the boundary with initial position and direction"
        monster.getPosition() >> new Position(4, 5)
        monster.getDirection() >> direction
        monster.getMaxX() >> maxX
        monster.getMinX() >> minX
        monster.getSizeX() >> 1
        monster.getSizeY() >> 1
        monster.getLastControlCount() >> 0

        level.isPositionFree(_ as Position) >> true
        level.getMonsters() >> [monster]

        when: "The MonsterController updates the monster's position"
        controller.update(Mock(GameManager), [] as Set, 0)

        then: "Monster direction and position should update accordingly"
        if (expectedDirectionChange) {
            1 * monster.setDirection(direction*(-1))
            1 * monster.setOrientation(expectedOrientation)
            1 * monster.setLastControlCount(0)
        } else {
            1 * monster.setPosition(new Position(expectedX, 5))
            1 * monster.setLastControlCount(1)
        }

        where:
        direction | maxX | minX | expectedX | expectedDirectionChange | expectedOrientation
        1         | 5    | 0    | 5         | true                   | Monster.ORIENTATION.LEFT
        -1        | 5    | 3    | 3         | true                   | Monster.ORIENTATION.RIGHT
        1         | 10   | 0    | 5         | false                  | null
        -1        | 10   | 0    | 3         | false                  | null
    }

    def "should reverse direction when monster collides with an obstacle"() {
        given: "A monster moving towards a position that is not free"
        monster.getPosition() >> new Position(3, 5)
        monster.getDirection() >> 1
        monster.getSizeX() >> 1
        monster.getSizeY() >> 1

        level.isPositionFree(new Position(4, 5)) >> false
        level.getMonsters() >> [monster]

        when: "The MonsterController updates the monster's position"
        controller.update(Mock(GameManager), [] as Set, 0)

        then: "Monster reverses direction and does not move"
        1 * monster.setDirection(-1)
        0 * monster.setPosition(_)
    }

    def "should update monster position when moving without obstacles"() {
        given: "A monster moving freely without collisions"
        monster.getPosition() >> new Position(3, 5)
        monster.getDirection() >> 1
        monster.getSizeX() >> 1
        monster.getSizeY() >> 1
        monster.getLastControlCount() >> 0

        level.isPositionFree(new Position(4, 5)) >> true
        level.getMonsters() >> [monster]

        when: "The MonsterController updates the monster's position"
        controller.update(Mock(GameManager), [] as Set, 0)

        then: "Monster moves to the next position and control count is incremented"
        1 * monster.setPosition(new Position(4, 5))
        1 * monster.setLastControlCount(1)
    }
}

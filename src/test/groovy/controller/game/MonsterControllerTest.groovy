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
        given:
            monster.getPosition() >> new Position(4, 5)
            monster.getDirection() >> direction
            monster.getMaxX() >> maxX
            monster.getMinX() >> minX
            monster.getSizeX() >> 1
            monster.getSizeY() >> 1
            monster.getLastControlCount() >> 0

            level.isPositionFree(_ as Position) >> true
            level.getMonsters() >> [monster]

        when:
            controller.update(Mock(GameManager), [] as Set, 0)

        then:
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

    def "move monster"() {
        given:
            monster.getPosition() >> new Position(4,5)
            monster.getDirection() >> 1
            monster.getSizeX() >> 2
            monster.getSizeY() >> 2
            Position expected = new Position(5,5)

        and:
            level.isPositionFree(new Position(5, 5)) >> true
            level.isPositionFree(new Position(5, 6)) >> true
            level.isPositionFree(new Position(6, 5)) >> true
            level.isPositionFree(new Position(6, 6)) >> true

        when:
            controller.moveMonster(monster, expected)

        then:
            1 * monster.setPosition(expected);
    }


    def "should reverse direction when monster collides with an obstacle"() {
        given:
            monster.getPosition() >> new Position(3, 5)
            monster.getDirection() >> 1
            monster.getSizeX() >> 1
            monster.getSizeY() >> 1

            level.isPositionFree(new Position(4, 5)) >> false
            level.getMonsters() >> [monster]

        when:
            controller.update(Mock(GameManager), [] as Set, 0)

        then:
            1 * monster.setDirection(-1)
            0 * monster.setPosition(_)
    }

    def "should update monster position when moving without obstacles"() {
        given:
            monster.getPosition() >> new Position(3, 5)
            monster.getDirection() >> 1
            monster.getSizeX() >> 1
            monster.getSizeY() >> 1
            monster.getLastControlCount() >> 0

            level.isPositionFree(new Position(4, 5)) >> true
            level.getMonsters() >> [monster]

        when:
            controller.update(Mock(GameManager), [] as Set, 0)

        then:
            1 * monster.setPosition(new Position(4, 5))
            1 * monster.setLastControlCount(1)
    }

    def "desiredX should behave correctly when == to monsterMaxX"() {
        given:
        level.getMonsters() >> [monster]
        monster.getPosition() >> new Position(319,5)
        monster.getDirection() >> 1
        monster.getMaxX() >> 320
        monster.getMinX() >> 0
        monster.getLastControlCount() >> 0

        when:
        controller.update(Mock(GameManager), [] as Set, 0)

        then:
        1 * monster.setPosition(new Position(320, 5))
        1 * monster.setLastControlCount(0)
    }

    def "desiredX should behave correctly when == to monsterMinX"() {
        given:
            level.getMonsters() >> [monster]
            monster.getPosition() >> new Position(1,5)
            monster.getDirection() >> -1
            monster.getMaxX() >> 6
            monster.getMinX() >> 0
            monster.getLastControlCount() >> 0

        when:
            controller.update(Mock(GameManager), [] as Set, 0)

        then:
            1 * monster.setPosition(new Position(0, 5))
            1 * monster.setLastControlCount(0)
    }
}


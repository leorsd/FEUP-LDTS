package model.elements.dynamicelements

import spock.lang.Specification
import model.Position

class MonsterTest extends Specification {

    def "test hashcode"() {
        given:
            def monster1 = new Monster(new Position(10, 10), 15, 15, 5, 20)
            def monster2 = new Monster(new Position(10, 10), 15, 15, 5, 20)
            def monster3 = new Monster(new Position(10, 10), 15, 15, 10, 25)
        expect:
            monster1.hashCode() == monster2.hashCode()
            monster2.hashCode() != monster3.hashCode()
    }

    def "should correctly compare monsters for equality"() {
        given:
            def monster1 = new Monster(new Position(1, 1), 10, 10, 5, 10)
            def monster2 = new Monster(new Position(1, 1), 10, 10, 5, 10)
            def monster3 = new Monster(new Position(1, 1), 10, 10, 10, 10)
            def monster4 = new Monster(new Position(1, 1), 10, 10, 5, 15)
            def monster5 = new Monster(new Position(1, 1), 10, 10, 5, 10)
            def monster6 = new Monster(new Position(1, 1), 10, 10, 5, 10)
            def monster7 = new Monster(new Position(10, 10), 10, 10, 5, 10)

            monster1.setOrientation(Monster.ORIENTATION.LEFT)
            monster2.setOrientation(Monster.ORIENTATION.LEFT)
            monster3.setOrientation(Monster.ORIENTATION.RIGHT)
            monster4.setOrientation(Monster.ORIENTATION.RIGHT)
            monster5.setDirection(2)
            monster6.setLastControlCount(1)
        expect:
            monster1.equals(monster1)
            monster1 == monster2
            monster1 != monster3
            monster1 != monster4
            monster1 != monster5
            monster1 != monster6
            monster1 != monster7
            !monster1.equals(20)
        when:
            monster2.setOrientation(Monster.ORIENTATION.RIGHT)
        then:
            monster1 != monster2
        when:
            monster2.setDirection(2)
        then:
            monster1 != monster2
        when:
            monster2.setMinX(6)
            monster2.setMaxX(12)
        then:
            monster1 != monster2
    }

    def "should correctly initialize monster with constructor"() {
        given: "A new Monster object"
        def position = new Position(5, 10)
        def monster = new Monster(position, 50, 60, 0, 100)

        expect: "The monster's properties should be initialized correctly"
        monster.getPosition() == position
        monster.getSizeX() == 50
        monster.getSizeY() == 60
        monster.getMinX() == 0
        monster.getMaxX() == 100
        monster.getOrientation() == Monster.ORIENTATION.RIGHT
        monster.getDirection() == 1
    }

    def "should update monster orientation"() {
        given: "A monster with an initial orientation"
        def monster = new Monster(new Position(5, 10), 50, 60, 0, 100)

        when: "The monster's orientation is set"
        monster.setOrientation(Monster.ORIENTATION.LEFT)

        then: "The monster's orientation should be updated"
        monster.getOrientation() == Monster.ORIENTATION.LEFT
    }

    def "should update monster direction"() {
        given: "A monster with an initial direction"
        def monster = new Monster(new Position(5, 10), 50, 60, 0, 100)

        when: "The monster's direction is set"
        monster.setDirection(-1)

        then: "The monster's direction should be updated"
        monster.getDirection() == -1
    }

    def "should update monster last control count"() {
        given: "A monster with an initial last control count"
        def monster = new Monster(new Position(5, 10), 50, 60, 0, 100)

        when: "The monster's last control count is set"
        monster.setLastControlCount(5)

        then: "The monster's last control count should be updated"
        monster.getLastControlCount() == 5
    }

    def "should update monster minX"() {
        given: "A monster with an initial minX"
        def monster = new Monster(new Position(5, 10), 50, 60, 0, 100)

        when: "The monster's minX is set"
        monster.setMinX(10)

        then: "The monster's minX should be updated"
        monster.getMinX() == 10
    }

    def "should update monster maxX"() {
        given: "A monster with an initial maxX"
        def monster = new Monster(new Position(5, 10), 50, 60, 0, 100)

        when: "The monster's maxX is set"
        monster.setMaxX(200)

        then: "The monster's maxX should be updated"
        monster.getMaxX() == 200
    }
}

package model.elements.dynamicelements

import spock.lang.Specification
import model.Position
import model.elements.dynamicelements.Monster

class MonsterTest extends Specification {

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

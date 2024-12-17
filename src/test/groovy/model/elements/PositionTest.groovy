package model.elements;
import model.Position
import spock.lang.Specification

class PositionTest extends Specification {

    def "should correctly set and get x and y values"() {
        given: "A new Position object"
        def position = new Position(5, 10)

        expect: "The x and y values should be correct"
        position.getX() == 5
        position.getY() == 10
    }

    def "should update x and y values using setters"() {
        given: "A Position object with initial values"
        def position = new Position(5, 10)

        when: "The x and y values are updated using setters"
        position.setX(15)
        position.setY(20)

        then: "The new x and y values should be set correctly"
        position.getX() == 15
        position.getY() == 20
    }

    def "should return true when positions are equal"() {
        given: "Two Position objects with the same coordinates"
        def position1 = new Position(5, 10)
        def position2 = new Position(5, 10)

        expect: "The two positions should be equal"
        position1.equals(position2)
    }

    def "should return false when positions are not equal"() {
        given: "Two Position objects with different coordinates"
        def position1 = new Position(5, 10)
        def position2 = new Position(10, 20)

        expect: "The two positions should not be equal"
        !position1.equals(position2)
    }

    def "should return consistent hash codes for equal positions"() {
        given: "Two Position objects with the same coordinates"
        def position1 = new Position(5, 10)
        def position2 = new Position(5, 10)

        expect: "The hash codes of equal positions should be the same"
        position1.hashCode() == position2.hashCode()
    }

    def "should return different hash codes for unequal positions"() {
        given: "Two Position objects with different coordinates"
        def position1 = new Position(5, 10)
        def position2 = new Position(10, 20)

        expect: "The hash codes of unequal positions should be different"
        position1.hashCode() != position2.hashCode()
    }
}

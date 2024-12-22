package model


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

    def "should return correct result for different Position comparisons"() {
        given: "Two Position objects"
        def position1 = new Position(x1, y1)
        def position2 = new Position(x2, y2)

        expect: "The result of comparison is as expected"
        position1.equals(position2) == expectedResult

        where:
        x1 | y1 | x2 | y2 | expectedResult
        10 | 20 | 10 | 20 | true
        10 | 20 | 15 | 20 | false
        10 | 20 | 10 | 25 | false
        10 | 20 | 15 | 25 | false
    }

    def "should return true when comparing same Position object"() {
        given: "A Position object"
        def position = new Position(10, 20)

        when: "The object is compared to itself"
        def result = position.equals(position)

        then: "It should return true"
        result
    }

    def "should return false when comparing Position with an object of a different class"() {
        given: "A Position object"
        def position = new Position(10, 20)
        def otherObject = new Object()

        when: "The Position object is compared to a different type of object"
        def result = position.equals(otherObject)

        then: "It should return false"
        !result
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

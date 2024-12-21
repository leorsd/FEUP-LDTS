package model.elements


import model.Position
import spock.lang.Specification

import java.awt.image.BufferedImage

class ElementTest extends Specification {

    //Class to instantiate the Element class because it is abstract, only for testing purposes
    class TestElement extends Element {
        TestElement(Position position, BufferedImage image, int sizeX, int sizeY) {
            super(position, sizeX, sizeY)
        }
    }

    def position
    def image
    def sizeX = 50
    def sizeY = 50
    def element

    def setup() {
        position = new Position(10, 10)
        image = Mock(BufferedImage)
        element = new TestElement(position, image, sizeX, sizeY)
    }

    def "test equals and hashCode"() {
        given:
            def element2 = new TestElement(position as Position, image as BufferedImage, sizeX, sizeY)
        expect:
            element == element2
        and:
            element.hashCode() == element2.hashCode()
        when:
            element2.setPosition(new Position(20, 20))
        then:
            element != element2
        and:
            element.hashCode() != element2.hashCode()
    }

    def "an element should be equal to himself"() {
        given:
            def element2 = element;
        expect:
            element.equals(element2)
    }

    def "an element can never be equal to an object that's not an instance of Element"() {
        given:
            def nonElementObject = "not an element"
        expect:
            !(element == nonElementObject)
    }

    def "test equals possibilities"(){
        given:
            def element2 = new TestElement(position as Position, image as BufferedImage, 5, 15)
        expect:
            element != element2
        when:
            element2.setSizeX(50)
        then:
            element != element2
        when:
            element2.setSizeY(50)
        then:
            element == element2
    }

    def "test hasCollided - all possible paths"() {
        expect:
        element.hasCollided(new Position(posX, posY), otherSizeX, otherSizeY) == expectedCollision

        where:
        posX | posY | otherSizeX | otherSizeY | expectedCollision

        100  | 10   | 1          | 1          | false
        10   | 100  | 1          | 1          | false
        200  | 200  | 1          | 1          | false
        0    | 0    | 1          | 1          | false
        10   | 0    | 10         | 10         | false

        10   | 10   | 50         | 50         | true
        10   | 10   | 40         | 40         | true
        50   | 10   | 1          | 50         | true
        10   | 50   | 50         | 1          | true

        60   | 10   | 1          | 50         | false
        10   | 60   | 50         | 1          | false
    }

    def "test isInside - all possible paths"() {
        given:
            def newElement = new TestElement(new Position(newElementPositionX,newElementPositionY),image as BufferedImage, newElementSizeX, newElementSizeY)
        expect:
            element.isInside(newElement) == expectedInside
        where:
        newElementPositionX | newElementPositionY | newElementSizeX | newElementSizeY | expectedInside

        0   | 0   | 10 | 10 | false
        5   | 5   | 60 | 60 | true
        0   | 10  | 10 | 60 | false
        10  | 0   | 60 | 10 | false
        10  | 10  | 50 | 50 | true
        10  | 20  | 50 | 50 | false
        20  | 10  | 50 | 50 | false
    }
}

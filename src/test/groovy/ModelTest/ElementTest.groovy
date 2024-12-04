package ModelTest

import Model.Elements.Element
import Model.Position
import spock.lang.Specification

import java.awt.image.BufferedImage

class ElementTest extends Specification {

    class TestElement extends Element {                                                   //Element is abstract so can't be instantiated
        TestElement(Position position, BufferedImage image, int sizeX, int sizeY) {       //We've created a TestElement for testing purposes
            super(position, image, sizeX, sizeY)
        }
    }

    def "test equals and hashCode"() {
        given: "Two elements with the same attributes"
        Position position = new Position(10, 10)
        BufferedImage image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB)
        int sizeX = 50
        int sizeY = 50
        Element element1 = new TestElement(position, image, sizeX, sizeY)
        Element element2 = new TestElement(position, image, sizeX, sizeY)

        expect: "The elements should be equal"       // In Groovy, When overriding the equals method, '==' performs a content-based comparison
        element1 == element2

        and: "The hash codes should be equal"
        element1.hashCode() == element2.hashCode()

        when: "Change one element's position"
        element2.setPosition(new Position(20, 20))

        then: "The elements should no longer be equal"
        element1 != element2

        and: "The hash codes should also differ"
        element1.hashCode() != element2.hashCode()
    }

    def "an element should be equal to himself"() {
        given: "An element"
        def elementPosition = new Position(10,10)
        int elementSizeX = 5
        int elementSizeY = 5
        TestElement testElement = new TestElement(elementPosition, new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB), elementSizeX, elementSizeY )

        expect: "the element should be equal to itself"
        testElement.equals(testElement)
    }

    def "an element can never be equal to an object that's not an instance of Element"() {
        given: "An element and an object that's not an instance of Element"
        def elementPosition = new Position(10,10)
        int elementSizeX = 5
        int elementSizeY = 5
        TestElement testElement = new TestElement(elementPosition, new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB), elementSizeX, elementSizeY)
        def nonElementObject = "not an element"

        expect: "they should never be equal"
        !(testElement == nonElementObject)
    }

    def "test hasCollided"() {
        given: "An element and a position with specific sizes"
        Position elementPosition = new Position(10, 10)
        Position collisionPosition = new Position(40, 40)
        int elementSizeX = 50
        int elementSizeY = 50
        int collisionSizeX = 10
        int collisionSizeY = 10

        TestElement element = new TestElement(elementPosition, new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB), elementSizeX, elementSizeY)

        expect: "The elements should collide"
        element.hasCollided(collisionPosition, collisionSizeX, collisionSizeY)

        when: "The position is far enough away"
        collisionPosition = new Position(100, 100)

        then: "There should be no collision"
        !element.hasCollided(collisionPosition, collisionSizeX, collisionSizeY)
    }
}

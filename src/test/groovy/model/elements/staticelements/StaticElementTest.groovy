package model.elements.staticelements

import model.Position
import model.elements.Element
import spock.lang.Specification

import java.awt.image.BufferedImage

class StaticElementTest extends Specification{
    def position
    def image
    def staticElement
    def mockElement

    def setup() {
        position = new Position(10, 20)
        image = Mock(BufferedImage)
        staticElement = new StaticElement(position, 30, 40, image as BufferedImage)
        mockElement = Mock(Element)
    }

    def "StaticElement should initialize with given parameters"() {
        expect:
            staticElement.getPosition() == position
            staticElement.sizeX == 30
            staticElement.sizeY == 40
            staticElement.getImage() == image
    }

    def "StaticElement should allow changing the image using setImage"() {
        given:
            def newImage = Mock(BufferedImage)
        when:
            staticElement.setImage(newImage)
        then:
            staticElement.getImage() == newImage
    }

    def "StaticElement should be equal to another StaticElement with the same attributes"() {
        given:
            def staticElement2 = new StaticElement(position as Position, 30, 40, image as BufferedImage)
        expect:
            staticElement == staticElement2
    }

    def "StaticElement should not be equal to another StaticElement with different image"() {
        given:
            def differentImage = Mock(BufferedImage)
            def staticElement2 = new StaticElement(position as Position, 30, 40, differentImage as BufferedImage)
        expect:
            staticElement != staticElement2
    }

    def "StaticElement should not be equal to another type of object"() {
        given:
            def nonStaticElement = new Object()
        expect:
            staticElement != nonStaticElement
    }

    def "StaticElement should generate correct hash code based on its attributes"() {
        given:
            def staticElement2 = new StaticElement(position as Position, 30, 40, image as BufferedImage)
        expect:
            staticElement.hashCode() == staticElement2.hashCode()
    }

    def "Static element equals should return true if is comparing the same object"(){
        given:
            def staticElement2 = staticElement
        expect:
            staticElement.equals(staticElement2)
    }

    def "StaticElement equals should return false if super.equals() returns false"() {
        given:
            def staticElement2 = new StaticElement(new Position(50,60), 20 , 30 , image as BufferedImage)
        expect:
            !staticElement.equals(staticElement2)
    }
}

package model.elements.staticelements

import model.Position
import spock.lang.Specification

import java.awt.image.BufferedImage

class ToggleableWallTest extends Specification{
    Position position
    BufferedImage image
    ToggleableWall wall

    def setup() {
        position = new Position(10, 20)
        image = Mock(BufferedImage)
        wall = new ToggleableWall(position, image, 30, 40)
    }

    def "test hashcode and equals"() {
        given:
        def wall1 = new Wall(new Position(10, 10), null, 15, 15)
        def wall2 = new Wall(new Position(10, 10), null, 15, 15)
        expect:
        wall1 == wall2
        wall1.hashCode() == wall2.hashCode()

    }

    def "ToggleableWall should initialize with given parameters and default active state true"() {
        expect:
            wall.getPosition() == position
            wall.sizeX == 30
            wall.sizeY == 40
            wall.isActive()
    }

    def "ToggleableWall should change active state when setActive is called"() {
        when:
            wall.setActive(false)
        then:
            !wall.isActive()
        when:
            wall.setActive(true)
        then:
            wall.isActive()
    }

    def "ToggleableWall should toggle active state when toggle is called"() {
       when:
            wall.toggle()
        then:
            !wall.isActive()
        when:
            wall.toggle()
        then:
            wall.isActive()
    }

    def "ToggleableWall should be equal to another ToggleableWall with the same attributes"() {
        given:
            def wall2 = new ToggleableWall(new Position(10,20), image, 30, 40)
        when:
            wall.setActive(true)
            wall2.setActive(true)
        then:
            wall == wall2
    }

    def "ToggleableWall should not be equal to another wall with different attributes"() {
       given:
            def wall2 = new ToggleableWall(new Position(20, 30), image, 30, 40)
        when:
            wall.setActive(true)
            wall2.setActive(true)
        then:
            wall != wall2
    }

    def "ToggleableWall should generate correct hash code based on its attributes"() {
        given:
            def position = new Position(10, 20)
            def image = Mock(BufferedImage)
            def wall1 = new ToggleableWall(position, image, 30, 40)
            def wall2 = new ToggleableWall(position, image, 30, 40)
        when:
            wall1.setActive(true)
            wall2.setActive(true)
        then:
            wall1.hashCode() == wall2.hashCode()
    }

    def "ToggleableWall equals should return true when compared to itself"() {
        given:
            def wall2 = wall
        expect:
            wall2.equals(wall)
    }

    def "ToggleableWall equals should return false when compared to an object of a different class"() {
        given:
            def otherObject = new Object()
        expect:
            wall != otherObject
    }

    def "ToggleableWall equals should return false when compared to ToggleableWall with different isActive state"() {
        given:
            def wall2 = new ToggleableWall(position as Position, image as BufferedImage, 30, 40)
        when:
            wall2.toggle()
        then:
            wall != wall2
    }
}

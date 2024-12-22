package model.elements.staticelements

import model.Position
import spock.lang.Specification

import java.awt.image.BufferedImage

class KeyTest extends Specification{
    def "test hashcode and equals"() {
        given:
            def Key1 = new Key(new Position(10, 10), null, 15, 15, "Lavena")
            def Key2 = new Key(new Position(10, 10), null, 15, 15, "Lavena")
        expect:
            Key1 == Key2
            Key1.hashCode() == Key2.hashCode()

    }

    def "Key should initialize with given parameters and default state not collected"() {
        given:
            def position = new Position(10, 20)
            def sizeX = 30
            def sizeY = 40
            def target = "DoorA"
            def image = Mock(BufferedImage)
        when:
            def key = new Key(position, image as BufferedImage, sizeX, sizeY, target)
        then:
            key.getPosition() == position
            key.sizeX == sizeX
            key.sizeY == sizeY
            key.getTarget() == target
            !key.isCollected()
    }

    def "Key should allow changing the target using setTarget"() {
        given:
            def key = new Key(new Position(0, 0), Mock(BufferedImage), 10, 10, "DoorA")
        when:
            key.setTarget("DoorB")
        then:
            key.getTarget() == "DoorB"
    }

    def "Key should allow changing the collected state using setCollected"() {
        given:
            def key = new Key(new Position(0, 0), Mock(BufferedImage), 10, 10, "DoorA")
        when:
            key.setCollected(true)
        then:
            key.isCollected()
        when:
            key.setCollected(false)
        then:
            !key.isCollected()
    }
}

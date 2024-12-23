package model.elements.staticelements

import model.Position
import spock.lang.Specification

import java.awt.image.BufferedImage

class KeyTest extends Specification{

    def "test hashcode"() {
        given:
            def key1 = new Key(new Position(10, 10), null, 15, 15, "Lavena")
            def key2 = new Key(new Position(10, 10), null, 15, 15, "Lavena")
            def key3 = new Key(new Position(10, 10), null, 15, 15, "Tergon")
        expect:
            key1.hashCode() == key2.hashCode()
            key2.hashCode() != key3.hashCode()
    }

    def "should correctly compare keys for equality"() {
        given:
            def key1 = new Key(new Position(1, 1), null, 10, 10, "Lavena")
            def key2 = new Key(new Position(1, 1), null, 10, 10, "Lavena")
            def key3 = new Key(new Position(2, 1), null, 10, 10, "Lavena")
            key1.setCollected(true)
            key2.setCollected(true)
            key3.setCollected(false)
        expect:
            key1.equals(key1)
            key1 != key3
            key1 == key2
            !key1.equals(20)
        when:
            key2.setTarget("Tergon")
        then:
            key1 != key2
        when:
            key2.setTarget("Lavena")
            key2.setCollected(false)
        then:
            key1 != key2
        when:
            key2.setTarget("Tergon")
        then:
            key1 != key2
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

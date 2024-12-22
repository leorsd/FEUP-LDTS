package model.elements.dynamicelements

import model.Position
import model.elements.staticelements.Key
import spock.lang.Specification

class DoorTest extends Specification{
    def "test hashcode and equals"() {
        given:
        def door1 = new Door(new Position(10, 10), 15, 15)
        def door2 = new Door(new Position(10, 10), 15, 15)
        expect:
        door1 == door2
        door1.hashCode() == door2.hashCode()

    }

    def "Door should initialize with given position and size and default state CLOSED"() {
        given:
            def position = new Position(10, 20)
            def sizeX = 30
            def sizeY = 40
        when:
            def door = new Door(position, sizeX, sizeY)
        then:
            door.getPosition() == position
            door.sizeX == sizeX
            door.sizeY == sizeY
            door.getState() == Door.STATE.CLOSED
    }

    def "Door state should change when setState is called"() {
        given:
            def door = new Door(new Position(0, 0), 10, 10)
        when:
            door.setState(Door.STATE.OPEN)
        then:
            door.getState() == Door.STATE.OPEN
        when:
            door.setState(Door.STATE.CLOSED)
        then:
            door.getState() == Door.STATE.CLOSED
    }
}

package model.elements.staticelements

import model.Position
import spock.lang.Specification

import java.awt.image.BufferedImage

class TrapTest extends Specification{
    def position
    def image
    def trap

    def setup() {
        position = new Position(10, 20)
        image = Mock(BufferedImage)
        trap = new Trap("Both", position, image, 30, 40)
    }

    def "test hashcode and equals"() {
        given:
        def trap1 = new Trap("Lavena", new Position(10, 10), null, 15, 15)
        def trap2 = new Trap("Lavena", new Position(10, 10), null, 15, 15)
        expect:
        trap1 == trap2
        trap1.hashCode() == trap2.hashCode()
    }

    def "Trap should initialize with given parameters"() {
        expect:
            trap.getPosition() == position
            trap.sizeX == 30
            trap.sizeY == 40
            trap.getTarget() == "Both"
    }

    def "Trap should allow changing the target using setTarget"() {
        when:
            trap.setTarget("Player1")
        then:
            trap.getTarget() == "Player1"
    }

    def "Trap should return the correct target using getTarget"() {
        expect:
            trap.getTarget() == "Both"
    }
}

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

    def "test hashcode"() {
        given:
            def trap1 = new Trap("Lavena", new Position(10, 10), null, 15, 15)
            def trap2 = new Trap("Lavena", new Position(10, 10), null, 15, 15)
            def trap3 = new Trap("Tergon", new Position(10, 10), null, 5, 15)
        expect:
            trap1.hashCode() == trap2.hashCode()
            trap2.hashCode() != trap3.hashCode()
    }

    def "should correctly compare traps for equality"() {
        given:
            def trap1 = new Trap("Lavena", new Position(1, 1), null, 10, 10)
            def trap2 = new Trap("Lavena", new Position(1, 1), null, 10, 10)
            def trap3 = new Trap("Tergon", new Position(1, 1), null, 10, 10)
            def trap4 = new Trap("Tergon", new Position(2, 1), null, 10, 10)
        expect:
            trap1.equals(trap1)
            trap1 == trap2
            !(trap1 == trap3)
            !(trap3 == trap4)
            !trap1.equals(20)
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

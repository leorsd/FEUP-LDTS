package model.elements.staticelements

import model.Position
import spock.lang.Specification

import java.awt.image.BufferedImage

class ButtonTest extends Specification{

    def "test hashcode"() {
        given:
            def toggleableWall = new ToggleableWall(new Position(1,1),null,1,1)
            def button1 = new Button(new Position(10, 10), null, 15, 15, null)
            def button2 = new Button(new Position(10, 10), null, 15, 15, null)
            def button3 = new Button(new Position(10, 10), null, 15, 15, toggleableWall)
        expect:
            button1.hashCode() == button2.hashCode()
            button2.hashCode() != button3.hashCode()
    }

    def "should correctly compare buttons for equality"() {
        given:
            def toggleableWall1 = new ToggleableWall(new Position(1,1),null,10,10)
            def toggleableWall2 = new ToggleableWall(new Position(10,10),null,5,5)
            def button1 = new Button(new Position(1, 1), null, 10, 10, toggleableWall1)
            def button2 = new Button(new Position(1, 1), null, 10, 10, toggleableWall1)
            def button3 = new Button(new Position(2, 1), null, 10, 10, toggleableWall1)
            def button4 = new Button(new Position(1, 1), null, 10, 10, toggleableWall2)
            button1.setPressed(true)
            button2.setPressed(true)
            button4.setPressed(false)
        expect:
            button1.equals(button1)
            button1 == button2
            button1 != button3
            button1 != button4
            !button1.equals(20)
        when:
            button2.setToggleableWall(toggleableWall2)
        then:
            button1 != button2
        when:
            button2.setPressed(false)
        then:
            button1 != button2
    }

    def "Button should initialize with given parameters and default state not pressed"() {
        given:
            def position = new Position(10, 20)
            def sizeX = 30
            def sizeY = 40
            def mockWall = Mock(ToggleableWall)
            def image = Mock(BufferedImage)
        when:
            def button = new Button(position, image, sizeX, sizeY, mockWall)
        then:
            button.getPosition() == position
            button.sizeX == sizeX
            button.sizeY == sizeY
            button.getToggleableWall() == mockWall
            !button.isPressed()
    }

    def "Button should change pressed state when setPressed is called"() {
        given:
            def button = new Button(new Position(0, 0), Mock(BufferedImage) as BufferedImage, 10, 10, Mock(ToggleableWall))
        when:
            button.setPressed(true)
        then:
            button.isPressed()
        when:
            button.setPressed(false)
        then:
            !button.isPressed()
    }

    def "Button should toggle its state and call toggle on associated wall when pressed"() {
        given:
            def mockWall = Mock(ToggleableWall)
            def button = new Button(new Position(0, 0), Mock(BufferedImage), 10, 10, mockWall)
        when:
            button.press()
        then:
            button.isPressed()
            1 * mockWall.toggle()
        when:
            button.press()
        then:
            !button.isPressed()
            1 * mockWall.toggle()
    }

    def "Button should interact with the newly set ToggleableWall after changing it"() {
        given:
            def mockWall1 = Mock(ToggleableWall)
            def mockWall2 = Mock(ToggleableWall)
            def button = new Button(new Position(0, 0), Mock(BufferedImage), 10, 10, mockWall1)
        when:
            button.setToggleableWall(mockWall2)

        and:
            button.press()
        then:
            0 * mockWall1.toggle()
            1 * mockWall2.toggle()
    }
}

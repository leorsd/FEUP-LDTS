package model.elements.staticelements

import model.Position
import spock.lang.Specification

import java.awt.image.BufferedImage

class ButtonTest extends Specification{
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

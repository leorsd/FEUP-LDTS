package gui

import com.googlecode.lanterna.TerminalSize
import com.googlecode.lanterna.TextColor
import com.googlecode.lanterna.screen.Screen
import com.googlecode.lanterna.graphics.TextGraphics
import gui.GUI.ACTION
import model.Position
import spock.lang.Specification
import javax.swing.JPanel
import java.awt.event.KeyEvent
import java.awt.image.BufferedImage

class LanternaGUITest extends Specification {

    Screen screen
    ScreenCreator screenCreator
    LanternaGUI gui

    def setup() {
        screen = Mock(Screen)
        screenCreator = Mock(ScreenCreator)
        screenCreator.createScreen(_) >> screen
        gui = new LanternaGUI(screenCreator)
    }

    def "test LanternaGUI creation and screen initialization"() {
        when: "LanternaGUI is initialized"
        gui = new LanternaGUI(screenCreator)

        then: "Screen initialization methods are not called"
        0 * screen.setCursorPosition(null)
        0 * screen.startScreen()
        0 * screen.doResizeIfNecessary()
    }

    def "test clear calls screen clear method"() {
        when:
        gui.start()
        gui.clear()
        then:
        1 * screen.clear()
    }

    def "test refresh calls screen refresh method"() {
        when:
        gui.start()
        gui.refresh()
        then:
        1 * screen.refresh()
    }

    def "test close calls screen close method"() {
        when:
        gui.start()
        gui.close()
        then:
        1 * screen.close()
    }

    def "test getGUIWidth and getGUIHeight"() {
        given:
        def screenCreator = Mock(ScreenCreator)
        def screen = Mock(Screen)
        def terminalSize = Mock(TerminalSize)

        screen.getTerminalSize() >> terminalSize
        terminalSize.getColumns() >> 80
        terminalSize.getRows() >> 24
        screenCreator.createScreen(_) >> screen

        when:
        def gui = new LanternaGUI(screenCreator)
        gui.start()

        then:
        gui.getGUIWidth() == 80
        gui.getGUIHeight() == 24
    }

    def "test drawImage"() {
        given: "A mocked ScreenCreator, Screen, and TextGraphics"
        def screenCreator = Mock(ScreenCreator)
        def screen = Mock(Screen)
        def graphics = Mock(TextGraphics)
        screen.newTextGraphics() >> graphics
        screenCreator.createScreen(_) >> screen

        and: "A LanternaGUI instance"
        def gui = new LanternaGUI(screenCreator)
        gui.start()

        and: "A BufferedImage to draw"
        def image = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB)
        image.setRGB(0, 0, (255 << 24) | (255 << 16)) // Fully opaque red
        image.setRGB(1, 0, (0 << 24)) // Fully transparent
        image.setRGB(0, 1, (255 << 24) | (255 << 8)) // Fully opaque green
        image.setRGB(1, 1, (255 << 24) | (255)) // Fully opaque blue

        and: "A position to draw the image"
        def position = new Position(5, 5)

        when: "drawImage is called"
        gui.drawImage(position, image)

        then: "TextGraphics is used to draw the image"
        1 * graphics.setCharacter(5, 5, { it.getForegroundColor() == new TextColor.RGB(255, 0, 0) })
        1 * graphics.setCharacter(5, 6, { it.getForegroundColor() == new TextColor.RGB(0, 255, 0) })
        1 * graphics.setCharacter(6, 6, { it.getForegroundColor() == new TextColor.RGB(0, 0, 255) })
        0 * graphics.setCharacter(6, 5, _)
    }

    def "test key press handling for all possible inputs"() {
        given:
        def source = new JPanel()
        def event = new KeyEvent(source, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, keyCode, ' ' as char)

        when:
        gui.keyAdapter.keyPressed(event)

        then: "The corresponding action is added to inputs"
        gui.getNextAction() == [expectedAction] as Set

        where:
        keyCode         | expectedAction
        KeyEvent.VK_UP  | ACTION.UP
        KeyEvent.VK_DOWN | ACTION.DOWN
        KeyEvent.VK_LEFT | ACTION.LEFT
        KeyEvent.VK_RIGHT | ACTION.RIGHT
        KeyEvent.VK_ENTER | ACTION.SELECT
        KeyEvent.VK_Q   | ACTION.QUIT
        KeyEvent.VK_W   | ACTION.W
        KeyEvent.VK_A   | ACTION.A
        KeyEvent.VK_S   | ACTION.S
        KeyEvent.VK_D   | ACTION.D
    }

    def "test key release handling for all possible inputs"() {
        given:
        def source = new JPanel()
        def pressEvent = new KeyEvent(source, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, keyCode, ' ' as char)
        def releaseEvent = new KeyEvent(source, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, keyCode, ' ' as char)

        when: "Key is pressed and then released"
        gui.keyAdapter.keyPressed(pressEvent) // Add the action first
        gui.keyAdapter.keyReleased(releaseEvent) // Then remove it

        then: "The corresponding action is removed from inputs"
        gui.getNextAction() == [] as Set // The set should be empty after key release

        where:
        keyCode         | expectedAction
        KeyEvent.VK_UP  | ACTION.UP
        KeyEvent.VK_DOWN | ACTION.DOWN
        KeyEvent.VK_LEFT | ACTION.LEFT
        KeyEvent.VK_RIGHT | ACTION.RIGHT
        KeyEvent.VK_ENTER | ACTION.SELECT
        KeyEvent.VK_Q   | ACTION.QUIT
        KeyEvent.VK_W   | ACTION.W
        KeyEvent.VK_A   | ACTION.A
        KeyEvent.VK_S   | ACTION.S
        KeyEvent.VK_D   | ACTION.D
    }
}
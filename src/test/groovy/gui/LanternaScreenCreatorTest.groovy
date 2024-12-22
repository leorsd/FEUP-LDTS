package gui

import com.googlecode.lanterna.TerminalSize
import com.googlecode.lanterna.terminal.DefaultTerminalFactory
import spock.lang.Specification
import java.awt.Dimension
import java.awt.event.KeyListener

class LanternaScreenCreatorTest extends Specification {

    def terminalSize
    def terminalFactory
    def defaultBounds

    def setup(){
        terminalSize = Mock(TerminalSize)
        terminalFactory = Mock(DefaultTerminalFactory)
        defaultBounds = new Dimension(800,600)
    }
    def "test screen width and height"() {
        given:
            terminalSize.getColumns() >> 80
            terminalSize.getRows() >> 24
            terminalFactory.setInitialTerminalSize(terminalSize)
        when:
            def screenCreator = new LanternaScreenCreator(terminalFactory as DefaultTerminalFactory, terminalSize as TerminalSize, defaultBounds as Dimension)
        then:
            screenCreator.getWidth() == 80
            screenCreator.getHeight() == 24
    }

    def "test font size calculation"() {
        given:
            terminalSize.getColumns() >> 80
            terminalSize.getRows() >> 24
            terminalFactory.setInitialTerminalSize(terminalSize)
        when:
            def screenCreator = new LanternaScreenCreator(terminalFactory as DefaultTerminalFactory, terminalSize as TerminalSize, defaultBounds as Dimension)
        then:
            def fontSize = screenCreator.getBestFontSize(defaultBounds)
            fontSize > 0
    }

    def "test screen creation with exception handling"() {
        given:
            def keyListener = Mock(KeyListener)
            terminalFactory.createScreen() >> { throw new IOException("Unable to create screen") }
        when:
            def exception = null
            try {
                def screenCreator = new LanternaScreenCreator(terminalFactory as DefaultTerminalFactory, terminalSize as TerminalSize, defaultBounds as Dimension)
                screenCreator.createScreen(keyListener)
            } catch (IOException e) {
                exception = e
            }
        then:
            exception != null
            exception.message == "Unable to create screen"
    }
}

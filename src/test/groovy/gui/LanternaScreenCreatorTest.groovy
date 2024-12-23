package gui

import com.googlecode.lanterna.TerminalSize
import com.googlecode.lanterna.screen.TerminalScreen
import com.googlecode.lanterna.terminal.DefaultTerminalFactory
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration
import com.googlecode.lanterna.terminal.swing.AWTTerminalFrame
import spock.lang.Specification

import java.awt.GraphicsConfiguration
import java.awt.Dimension
import java.awt.GraphicsDevice
import java.awt.Toolkit
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
            exception.message == "Error when trying to create Lanterna screen: Unable to create screen"
    }

    def "test getAwtTerminalFrame with full screen supported"() {
        given:
            def screen = Mock(TerminalScreen)
            def gd = Mock(GraphicsDevice)
            def awtTerminalFrame = Mock(AWTTerminalFrame)
            def lanternaScreenCreator = new LanternaScreenCreator(terminalFactory as DefaultTerminalFactory, terminalSize as TerminalSize, defaultBounds as Dimension)
            gd.isFullScreenSupported() >> true
            screen.getTerminal() >> awtTerminalFrame
        expect:
            lanternaScreenCreator.getAwtTerminalFrame(screen, gd) == awtTerminalFrame
    }

    def "test getAwtTerminalFrame with full screen not supported"() {
        given:
            def screen = Mock(TerminalScreen)
            def gd = Mock(GraphicsDevice)
            def awtTerminalFrame = Mock(AWTTerminalFrame)
            def lanternaScreenCreator = new LanternaScreenCreator(terminalFactory as DefaultTerminalFactory, terminalSize as TerminalSize, defaultBounds as Dimension)
            gd.isFullScreenSupported() >> false
            screen.getTerminal() >> awtTerminalFrame
        expect:
            lanternaScreenCreator.getAwtTerminalFrame(screen, gd) == awtTerminalFrame
    }

    def "load font from wrong file"() {
        given:
            def lanternaScreenCreator = new LanternaScreenCreator(terminalFactory as DefaultTerminalFactory, terminalSize as TerminalSize, defaultBounds as Dimension)
            def exception
        when:
            try {
                lanternaScreenCreator.loadFont(7, "levels/credits")
            } catch (Exception e) {
                exception = e
            }
        then:
            exception != null
            exception.message.substring(0, 61) == "Error while creating Lanterna screen when trying to load font"
    }

    def "load font from nonexistent file"() {
        given:
        def lanternaScreenCreator = new LanternaScreenCreator(terminalFactory as DefaultTerminalFactory, terminalSize as TerminalSize, defaultBounds as Dimension)
        def exception
        when:
        try {
            lanternaScreenCreator.loadFont(7, "levels/creditos")
        } catch (Exception e) {
            exception = e
        }
        then:
        exception != null
        exception.message.substring(0, 66) == "Error while creating Lanterna screen when trying to open font file"
    }
}

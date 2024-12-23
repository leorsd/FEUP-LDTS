package gui

import com.googlecode.lanterna.TerminalSize
import com.googlecode.lanterna.screen.TerminalScreen
import com.googlecode.lanterna.terminal.DefaultTerminalFactory
import com.googlecode.lanterna.terminal.swing.AWTTerminalFrame
import spock.lang.Specification

import java.awt.Dimension
import java.awt.GraphicsDevice

class LanternaSCreatorNotPitestable extends Specification {

    def terminalSize
    def terminalFactory
    def defaultBounds

    def setup(){
        terminalSize = Mock(TerminalSize)
        terminalFactory = Mock(DefaultTerminalFactory)
        defaultBounds = new Dimension(800,600)
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
}

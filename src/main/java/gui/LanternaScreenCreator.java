package gui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFrame;

import java.awt.*;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

public class LanternaScreenCreator implements ScreenCreator {
    private final DefaultTerminalFactory terminalFactory;
    private final TerminalSize terminalSize;
    private final Dimension defaultBounds;

    public LanternaScreenCreator(DefaultTerminalFactory terminalFactory, TerminalSize terminalSize, Dimension defaultBounds) {
        this.terminalFactory = terminalFactory;
        this.terminalSize = terminalSize;
        this.defaultBounds = defaultBounds;
        terminalFactory.setInitialTerminalSize(terminalSize);
        terminalFactory.setForceAWTOverSwing(true);
    }

    @Override
    public Screen createScreen(KeyListener keyListener) throws IOException {
        int fontSize = getBestFontSize(defaultBounds);
        AWTTerminalFontConfiguration fontConfig = loadFont(fontSize);
        terminalFactory.setTerminalEmulatorFontConfiguration(fontConfig);
        TerminalScreen screen;
        try {
            screen = terminalFactory.createScreen();
        } catch (IOException e) {
            throw new IOException("Error when trying to create Lanterna screen: " + e.getMessage());
        }
        AWTTerminalFrame terminal = getAwtTerminalFrame(screen);
        terminal.getComponent(0).addKeyListener(keyListener);
        return screen;
    }

    private AWTTerminalFrame getAwtTerminalFrame(TerminalScreen screen) {
        AWTTerminalFrame terminal = (AWTTerminalFrame) screen.getTerminal();
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (gd.isFullScreenSupported()) {
            gd.setFullScreenWindow(terminal);
        }else {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            terminal.setSize(screenSize);
            terminal.setLocation(0, 0);
        }
        return terminal;
    }

    private AWTTerminalFontConfiguration loadFont(int fontSize) throws IOException {
        URL resource = getClass().getClassLoader().getResource("fonts/square.ttf");
        File fontFile;
        try {
            fontFile = new File(Objects.requireNonNull(resource).toURI());
        } catch (URISyntaxException e) {
            throw new IOException("Syntax error while creating Lanterna screen when trying to open font file: " + e.getMessage());
        }
        Font font;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Font.PLAIN, fontSize);
        } catch (Exception e) {
            throw new IOException("Error while creating Lanterna screen when trying to load font: " + e.getMessage() + " cause : " +  e.getCause());
        }
        return AWTTerminalFontConfiguration.newInstance(font);
    }

    private int getBestFontSize(Dimension terminalBounds) {
        double maxFontWidth = terminalBounds.getWidth() / terminalSize.getColumns();
        double maxFontHeight = terminalBounds.getHeight() / terminalSize.getRows();
        return (int) Math.min(maxFontWidth, maxFontHeight);
    }

    @Override
    public int getWidth() {
        return terminalSize.getColumns();
    }

    @Override
    public int getHeight() {
        return terminalSize.getRows();
    }
}

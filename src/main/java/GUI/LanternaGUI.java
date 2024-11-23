package GUI;

import Model.Elements.Characters.Monster;
import Model.Elements.Characters.Player;
import Model.Elements.Key;
import Model.Elements.Trap;
import Model.Elements.Wall;
import Model.Position;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFrame;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;

import static GUI.GUI.ACTION.*;

public class LanternaGUI implements GUI {
    private final Screen screen;
    private final HashSet<ACTION> inputs = new HashSet<>();

    public LanternaGUI(int width, int height) throws IOException, FontFormatException, URISyntaxException {
        AWTTerminalFontConfiguration fontConfig = loadSquareFont();
        Terminal terminal = createTerminal(width, height, fontConfig);
        this.screen = createScreen(terminal);
        initializeKeyListener();
    }

    private Screen createScreen(Terminal terminal) throws IOException {
        final Screen screen;
        screen = new TerminalScreen(terminal);

        screen.setCursorPosition(null);
        screen.startScreen();
        screen.doResizeIfNecessary();
        return screen;
    }

    private Terminal createTerminal(int width, int height, AWTTerminalFontConfiguration fontConfig) throws IOException {
        TerminalSize terminalSize = new TerminalSize(width, height);
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
        terminalFactory.setForceAWTOverSwing(true);
        terminalFactory.setTerminalEmulatorFontConfiguration(fontConfig);
        return terminalFactory.createTerminal();
    }

    private AWTTerminalFontConfiguration loadSquareFont() throws URISyntaxException, FontFormatException, IOException {
        URL resource = getClass().getClassLoader().getResource("fonts/square.ttf");
        File fontFile = new File(resource.toURI());
        Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(font);

        Font loadedFont = font.deriveFont(Font.PLAIN, 5);
        AWTTerminalFontConfiguration fontConfig = AWTTerminalFontConfiguration.newInstance(loadedFont);
        return fontConfig;
    }

    private void initializeKeyListener() {
        AWTTerminalFrame terminalFrame = (AWTTerminalFrame) screen;
        terminalFrame.getComponent(0).addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }
            @Override
            public void keyReleased(KeyEvent e) {
                handleKeyRelease(e);
            }
        });
    }

    @Override
    public void drawPlayer(Player player) {
        drawImage(player.getPosition(),player.getImage());
    }

    @Override
    public void drawKey(Key key) {
        drawImage(key.getPosition(),key.getImage());
    }

    @Override
    public void drawMonster(Monster monster) {
        drawImage(monster.getPosition(),monster.getImage());
    }

    @Override
    public void drawWall(Wall wall) {
        drawImage(wall.getPosition(),wall.getImage());
    }

    @Override
    public void drawText(Position position, BufferedImage image) {
        drawImage(position,image);
    }

    @Override
    public void drawTrap(Trap trap) {
        drawImage(trap.getPosition(),trap.getImage());
    }

    @Override
    public void clear() {
        screen.clear();
    }

    @Override
    public void close() throws IOException {
        screen.close();
    }

    @Override
    public void refresh() throws IOException {
        screen.refresh();
    }

    public HashSet<ACTION> getNextAction() throws IOException {
        return new HashSet<>(inputs);
    }

    private void handleKeyPress(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                inputs.add(LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                inputs.add(RIGHT);
                break;
            case KeyEvent.VK_UP:
                inputs.add(UP);
                break;
            case KeyEvent.VK_DOWN:
                inputs.add(DOWN);
                break;
            case KeyEvent.VK_ENTER:
                inputs.add(SELECT);
                break;
            case KeyEvent.VK_Q:
                inputs.add(QUIT);
                break;
            case KeyEvent.VK_W:
                inputs.add(W);
                break;
            case KeyEvent.VK_S:
                inputs.add(S);
                break;
            case KeyEvent.VK_A:
                inputs.add(A);
                break;
            case KeyEvent.VK_D:
                inputs.add(D);
                break;
            default:
                break;
        }
    }

    private void handleKeyRelease(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                inputs.remove(LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                inputs.remove(RIGHT);
                break;
            case KeyEvent.VK_UP:
                inputs.remove(UP);
                break;
            case KeyEvent.VK_DOWN:
                inputs.remove(DOWN);
                break;
            case KeyEvent.VK_ENTER:
                inputs.remove(SELECT);
                break;
            case KeyEvent.VK_Q:
                inputs.remove(QUIT);
                break;
            case KeyEvent.VK_W:
                inputs.remove(W);
                break;
            case KeyEvent.VK_S:
                inputs.remove(S);
                break;
            case KeyEvent.VK_A:
                inputs.remove(A);
                break;
            case KeyEvent.VK_D:
                inputs.remove(D);
                break;
            default:
                break;
        }
    }

    private void drawImage(Position position, BufferedImage image){
        TextGraphics graphics = screen.newTextGraphics();
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int a = image.getRGB(x, y);
                int alpha = (a >> 24) & 0xff;
                int red = (a >> 16) & 0xff;
                int green = (a >> 8) & 0xff;
                int blue = a & 0xff;
                if (alpha != 0) {
                    graphics.setCharacter(x+position.getX(), y+position.getY(), new TextCharacter(' ', new TextColor.RGB(red, green, blue), new TextColor.RGB(red, green, blue)));
                }
            }
        }
    }
}

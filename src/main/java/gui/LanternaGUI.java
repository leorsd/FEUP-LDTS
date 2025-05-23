package gui;

import model.Position;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static gui.GUI.ACTION.*;

public class LanternaGUI implements GUI {
    private final ScreenCreator screenCreator;
    private Screen screen;
    private final KeyAdapter keyAdapter;
    private final HashSet<ACTION> inputs = new HashSet<>();

    public LanternaGUI(ScreenCreator screenCreator) {
        this.screenCreator = screenCreator;
        this.keyAdapter = createKeyAdapter();
    }

    private KeyAdapter createKeyAdapter() {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }
            @Override
            public void keyReleased(KeyEvent e) {
                handleKeyRelease(e);
            }
        };
    }

    @Override
    public int getGUIWidth() {
        return screen.getTerminalSize().getColumns();
    }

    @Override
    public int getGUIHeight() {
        return screen.getTerminalSize().getRows();
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
        try {
            screen.refresh();
        } catch (IOException e) {
            throw new IOException("Error while refreshing Lanterna screen in LanternaGUI: " + e.getMessage() + " cause: " + e.getCause());
        }
    }

    @Override
    public void start() throws IOException {
        screen = screenCreator.createScreen(keyAdapter);
        screen.setCursorPosition(null);
        try {
            screen.startScreen();
        } catch (IOException e) {
            throw new IOException("Error while starting Lanterna screen in LanternaGUI: " + e.getMessage() + " cause: " + e.getCause());
        }
        screen.doResizeIfNecessary();
    }

    @Override
    public Set<ACTION> getNextAction(){
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

    @Override
    public void drawImage(Position position, BufferedImage image){
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

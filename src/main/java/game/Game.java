package game;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import gui.LanternaGUI;
import gui.LanternaScreenCreator;
import gui.ScreenCreator;
import model.scenes.Menu;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;


public class Game {
    private static Game gameInstance;
    private final LanternaGUI gui;
    private GameManager gameManager;

    private Game() throws FontFormatException, IOException, URISyntaxException {
        ScreenCreator screenCreator = new LanternaScreenCreator(
                new DefaultTerminalFactory(),
                new TerminalSize(240, 135),
                GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds()
        );
        this.gui = new LanternaGUI(screenCreator);
        this.gameManager = new GameManager(new Menu());
    }

    public static synchronized Game getInstance() throws FontFormatException, IOException, URISyntaxException {
        if (gameInstance == null) {
            gameInstance = new Game();
        }
        return gameInstance;
    }

    public static void main(String[] args) {
        try {
            Game game = Game.getInstance(); // Use the singleton instance
            game.start();
        } catch (IOException | FontFormatException | URISyntaxException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    private void start() throws IOException {
        int FPS = 30;
        int frameTime = 1000 / FPS;
        boolean running = true;

        while (running) {
            long startTime = System.currentTimeMillis();

            running = gameManager.step(gui, startTime);

            long elapsedTime = System.currentTimeMillis() - startTime;
            long sleepTime = frameTime - elapsedTime;

            try {
                if (sleepTime > 0) Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        gui.close();
    }
}

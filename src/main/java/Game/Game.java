package Game;

import GUI.LanternaGUI;
import Model.Scenes.Menu;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;


public class Game {
    private static Game gameInstance; // Singleton design pattern
    private final LanternaGUI gui;
    private GameManager gameManager;

    private Game() throws FontFormatException, IOException, URISyntaxException {
        this.gui = new LanternaGUI(300, 300);
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
            e.printStackTrace();
        }
    }

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    private void start() throws IOException {
        int FPS = 10;
        int frameTime = 1000 / FPS;

        while (this.gameManager != null) {
            long startTime = System.currentTimeMillis();

            gameManager.step(gui, startTime);

            long elapsedTime = System.currentTimeMillis() - startTime;
            long sleepTime = frameTime - elapsedTime;

            try {
                if (sleepTime > 0) Thread.sleep(sleepTime);
            } catch (InterruptedException ignored) {
            }
        }

        gui.close();
    }
}

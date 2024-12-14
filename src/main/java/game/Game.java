package game;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import gui.LanternaGUI;
import gui.LanternaScreenCreator;
import gui.ScreenCreator;
import model.scenes.Menu;
import sound.BackgroundSoundPlayer;
import sound.SoundLoader;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;


public class Game {
    private static Game gameInstance;
    private final LanternaGUI gui;
    private GameManager gameManager;
    private BackgroundSoundPlayer backgroundSoundPlayer;

    private Game() throws Exception {
        ScreenCreator screenCreator = new LanternaScreenCreator(
                new DefaultTerminalFactory(),
                new TerminalSize(320, 180),
                Toolkit.getDefaultToolkit().getScreenSize()
        );
        this.gui = new LanternaGUI(screenCreator);
        this.gameManager = new GameManager(new Menu());
        this.backgroundSoundPlayer = new BackgroundSoundPlayer(new SoundLoader().loadSound(AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getClassLoader().getResource("sounds/powerup.wav"))), AudioSystem.getClip()));

        FloatControl gainControl = (FloatControl) backgroundSoundPlayer.getSound().getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-15f);
    }

    public static synchronized Game getInstance() throws Exception {
        if (gameInstance == null) {
            gameInstance = new Game();
        }
        return gameInstance;
    }

    public static void main(String[] args) {
        try {
            Game game = Game.getInstance(); // Use the singleton instance
            game.start();
        } catch (IOException | FontFormatException | URISyntaxException | UnsupportedAudioFileException | LineUnavailableException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    private void start() throws IOException {
        int FPS = 20;
        int frameTime = 1000 / FPS;
        boolean running = true;

        backgroundSoundPlayer.start();
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
        backgroundSoundPlayer.stop();
        gui.close();
    }
}

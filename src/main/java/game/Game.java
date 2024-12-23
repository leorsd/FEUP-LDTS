package game;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import gui.GUI;
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
    private GUI gui;
    private GameManager gameManager;
    private BackgroundSoundPlayer backgroundSoundPlayer;

    private Game(GUI gui, GameManager gameManager, BackgroundSoundPlayer backgroundSoundPlayer) {
        this.gui = gui;
        this.gameManager = gameManager;
        this.backgroundSoundPlayer = backgroundSoundPlayer;
    }

    public static synchronized Game getInstance(GUI gui, GameManager gameManager, BackgroundSoundPlayer backgroundSoundPlayer) throws Exception {
        if (gameInstance == null) {
            if (gui == null) {
                gui = createDefaultGUI();
            }
            if (gameManager == null) {
                gameManager = createDefaultGameManager();
            }
            if (backgroundSoundPlayer == null) {
                backgroundSoundPlayer = createDefaultBackgroundSoundPlayer();
            }
            gameInstance = new Game(gui, gameManager, backgroundSoundPlayer);
        }
        return gameInstance;
    }

    private static LanternaGUI createDefaultGUI() throws Exception {
        ScreenCreator screenCreator = new LanternaScreenCreator(
                new DefaultTerminalFactory(),
                new TerminalSize(320, 180),
                Toolkit.getDefaultToolkit().getScreenSize()
        );
        return new LanternaGUI(screenCreator);
    }

    private static GameManager createDefaultGameManager() throws IOException {
        return new GameManager(new Menu());
    }

    private static BackgroundSoundPlayer createDefaultBackgroundSoundPlayer() throws Exception {
        BackgroundSoundPlayer soundPlayer = new BackgroundSoundPlayer(
                new SoundLoader().loadSound(AudioSystem.getAudioInputStream(
                        Objects.requireNonNull(Game.class.getClassLoader().getResource("sounds/powerup!.wav"))
                ), AudioSystem.getClip())
        );
        FloatControl gainControl = (FloatControl) soundPlayer.getSound().getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-15f);
        return soundPlayer;
    }

    public static void main(String[] args) {
        try {
            Game game = Game.getInstance(null,null,null); // Use the singleton instance
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

    public void setGui(GUI gui) {
        this.gui=gui;
    }

    public void setBackgroundSoundPlayer(BackgroundSoundPlayer backgroundSoundPlayer) {
        this.backgroundSoundPlayer = backgroundSoundPlayer;
    }

    public GUI getGui() {
        return gui;
    }

    public BackgroundSoundPlayer getBackgroundSoundPlayer() {
        return backgroundSoundPlayer;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public void start() throws IOException, URISyntaxException, FontFormatException {
        int FPS = 20;
        int frameTime = 1000 / FPS;
        boolean running = true;

        gui.start();
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

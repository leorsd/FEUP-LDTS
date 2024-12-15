package controller.game;

import game.GameManager;
import model.Position;
import model.elements.Button;
import model.elements.Key;
import model.elements.ToggleableWall;
import model.elements.movingelements.Monster;
import model.elements.movingelements.Player;
import model.elements.Trap;
import model.scenes.Level;
import model.scenes.LevelLoader;
import model.scenes.Menu;
import controller.Controller;
import gui.GUI;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class LevelController extends Controller<Level> {
    private final Player1Controller player1Controller;
    private final Player2Controller player2Controller;
    private final MonsterController monsterController;

    public LevelController(Level level) {
        super(level);

        this.player1Controller = new Player1Controller(level);
        this.player2Controller = new Player2Controller(level);
        this.monsterController = new MonsterController(level);
    }

    private boolean checkPlayerDead(Player player) {
        boolean result = false;
        for (Monster monster : getModel().getMonsters()) {
            result |= player.hasCollided(monster.getPosition(), monster.getSizeX(), monster.getSizeY());
        }
        for (Trap trap : getModel().getTraps()) {
            result |= (player.hasCollided(trap.getPosition(), trap.getSizeX(), trap.getSizeY()) && (player.getName().equals(trap.getTarget()) || trap.getTarget().equals("Both")));
        }
        for (ToggleableWall wall : getModel().getToggleableWalls()) {
            result |= player.hasCollided(wall.getPosition(), wall.getSizeX(), wall.getSizeY()) && wall.isActive();
        }
        return result;
    }

    private void checkButtonsClicked() {
        HashMap<ToggleableWall, Boolean> activeWallsTemp = new HashMap<>();
        for (ToggleableWall wall : getModel().getToggleableWalls()) {
            activeWallsTemp.put(wall, true);
        }
        for (Button button : getModel().getButtons()) {
            if (button.hasCollided(getModel().getPlayer1().getPosition(), getModel().getPlayer1().getSizeX(), getModel().getPlayer1().getSizeY()) || button.hasCollided(getModel().getPlayer2().getPosition(), getModel().getPlayer2().getSizeX(), getModel().getPlayer2().getSizeY())) {
                button.setPressed(true);
                activeWallsTemp.put(button.getToggleableWall(), false);
            } else {
                button.setPressed(false);
            }
        }
        for (ToggleableWall wall : getModel().getToggleableWalls()) {
            if (activeWallsTemp.get(wall)) {
                wall.setActive(true);
            } else {
                wall.setActive(false);
            }
        }
    }

    private void collectKeys() {
        String player1Name = getModel().getPlayer1().getName();
        Position player1Position = getModel().getPlayer1().getPosition();
        int player1SizeX = getModel().getPlayer1().getSizeX();
        int player1SizeY = getModel().getPlayer1().getSizeY();
        String player2Name = getModel().getPlayer2().getName();
        Position player2Position = getModel().getPlayer2().getPosition();
        int player2SizeX = getModel().getPlayer2().getSizeX();
        int player2SizeY = getModel().getPlayer2().getSizeY();
        for (Key key : getModel().getKeys()) {
            if ((key.getTarget().equals(player1Name) && key.hasCollided(player1Position, player1SizeX, player1SizeY)) || (key.getTarget().equals(player2Name) && key.hasCollided(player2Position, player2SizeX, player2SizeY))) {
                key.setCollected(true);
            }
        }
    }

    private boolean allKeysCollected () {
        for (Key key : getModel().getKeys()) {
            if (!key.isCollected()) {
                return false;
            }
        }
        return true;
    }

    private void playerDied(Player player) {
        player.setPosition(getModel().getPlayerSpawnPosition(player));
        for (Key key : getModel().getKeys()) {
            if (key.getTarget().equals(player.getName()) && key.isCollected()) {
                key.setCollected(false);
            }
        }
    }

    private boolean checkLevelTransition() {
        return getModel().getPlayer1().isInside(getModel().getLevelEndingWall()) && getModel().getPlayer2().isInside(getModel().getLevelEndingWall()) && allKeysCollected();
    }

    @Override
    public void update(GameManager gameManager, Set<GUI.ACTION> actions, long updateTime) throws IOException {
        if (actions.contains(GUI.ACTION.QUIT)) {
            gameManager.setCurrentScene(new Menu());
            return;
        }
        Set<GUI.ACTION> player1Actions = new HashSet<>();
        Set<GUI.ACTION> player2Actions = new HashSet<>();

        player1Actions.add(GUI.ACTION.DOWN);
        player2Actions.add(GUI.ACTION.S);
        for (GUI.ACTION action : actions) {
            if (action == GUI.ACTION.UP || action == GUI.ACTION.RIGHT || action == GUI.ACTION.LEFT) {
                player1Actions.add(action);
            }
            if (action == GUI.ACTION.W || action == GUI.ACTION.D || action == GUI.ACTION.A) {
                player2Actions.add(action);
            }
        }
        player1Controller.update(gameManager, player1Actions, updateTime);
        player2Controller.update(gameManager, player2Actions, updateTime);
        collectKeys();

        checkButtonsClicked();

        if (checkPlayerDead(getModel().getPlayer1())) {
            playerDied(getModel().getPlayer1());
            player1Controller.setJumping(false);
        }
        if (checkPlayerDead(getModel().getPlayer2())) {
            playerDied(getModel().getPlayer2());
            player2Controller.setJumping(false);
        }

        if (checkLevelTransition()) {
            if (Objects.equals(getModel().getNextLevel(), "menu")) {
                gameManager.setCurrentScene(new Menu());
            }
            else {
                gameManager.setCurrentScene(new LevelLoader().loadLevel(getModel().getNextLevel()));
            }
            return;
        }
        monsterController.update(gameManager, actions, updateTime);
        if (checkPlayerDead(getModel().getPlayer1())) {
            playerDied(getModel().getPlayer1());
            player1Controller.setJumping(false);
        }
        if (checkPlayerDead(getModel().getPlayer2())) {
            playerDied(getModel().getPlayer2());
            player2Controller.setJumping(false);
        }
    }
}
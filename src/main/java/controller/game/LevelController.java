package controller.game;

import game.GameManager;
import model.elements.movingelements.Monster;
import model.elements.movingelements.Player;
import model.elements.Trap;
import model.scenes.Level;
import model.scenes.Menu;
import controller.Controller;
import gui.GUI;

import java.io.IOException;
import java.util.HashSet;
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

    private boolean checkDeadPlayers() {
        Player player1 = getModel().getPlayer1();
        Player player2 = getModel().getPlayer2();
        boolean result = false;
        for (Monster monster : getModel().getMonsters()) {
            result |= player1.hasCollided(monster.getPosition(), monster.getSizeX(), monster.getSizeY()) || player2.hasCollided(monster.getPosition(), monster.getSizeX(), monster.getSizeY());
        }
        for (Trap trap : getModel().getTraps()) {
            result |= (player1.hasCollided(trap.getPosition(), trap.getSizeX(), trap.getSizeY()) && player1.getName().equals(trap.getTarget())) || (player2.hasCollided(trap.getPosition(), trap.getSizeX(), trap.getSizeY()) && player2.getName().equals(trap.getTarget()));
        }
        return result;
    }

    private boolean checkLevelTransition() {
        return getModel().getPlayer1().isInside(getModel().getLevelEndingWall()) && getModel().getPlayer2().isInside(getModel().getLevelEndingWall()) && getModel().getKeys().isEmpty();
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
        if (checkDeadPlayers()) {
            // TODO: restart level instead of going to menu
            gameManager.setCurrentScene(new Menu());
            return;
        }
        if (checkLevelTransition()) {
            gameManager.setCurrentScene(new Menu());
            return;
        }
        monsterController.update(gameManager, actions, updateTime);
        if (checkDeadPlayers()) {
            gameManager.setCurrentScene(new Menu());
        }
    }
}
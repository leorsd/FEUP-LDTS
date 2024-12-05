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

    @Override
    public void update(GameManager gameManager, Set<GUI.ACTION> actions, long updateTime) throws IOException {
        if (actions.contains(GUI.ACTION.QUIT)) {
            gameManager.setCurrentScene(new Menu());
            return;
        }
        player1Controller.update(gameManager, Set.of(GUI.ACTION.DOWN), updateTime);
        player2Controller.update(gameManager, Set.of(GUI.ACTION.S), updateTime);
        for (GUI.ACTION action : actions) {
            if (action == GUI.ACTION.UP || action == GUI.ACTION.RIGHT || action == GUI.ACTION.LEFT) {
                player1Controller.update(gameManager, Set.of(action), updateTime);
            }
            if (action == GUI.ACTION.W || action == GUI.ACTION.D || action == GUI.ACTION.A) {
                player2Controller.update(gameManager, Set.of(action), updateTime);
            }
        }
        if (checkDeadPlayers()) {
            // TODO: restart level instead of going to menu
            gameManager.setCurrentScene(new Menu());
            return;
        }
        monsterController.update(gameManager, actions, updateTime);
        if (checkDeadPlayers()) {
            // Also check for dead players after moving them and monsters
            gameManager.setCurrentScene(new Menu());
        }
    }
}
package controller.game;

import Game.GameManager;
import Model.Elements.Characters.Monster;
import Model.Position;
import Model.Scenes.Level;
import Model.Scenes.Menu;
import controller.Controller;
import GUI.GUI;

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

    private boolean checkDeadPlayers(GameManager gameManager) {
        Position player1Position = getModel().getPlayer1().getPosition();
        Position player2Position = getModel().getPlayer2().getPosition();
        for (Monster monster : getModel().getMonsters()) {
            if (monster.getPosition().equals(player1Position) || monster.getPosition().equals(player2Position)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void update(GameManager gameManager, Set<GUI.ACTION> actions, long updateTime) throws IOException {
        if (actions.contains(GUI.ACTION.QUIT)) {
            gameManager.setCurrentScene(new Menu());
            return;
        }
        if (!(actions.contains(GUI.ACTION.UP) || actions.contains(GUI.ACTION.DOWN) || actions.contains(GUI.ACTION.LEFT) || actions.contains(GUI.ACTION.RIGHT))) {
            player1Controller.update(gameManager, Set.of(GUI.ACTION.DOWN), updateTime);
        }
        if (!(actions.contains(GUI.ACTION.W) || actions.contains(GUI.ACTION.S) || actions.contains(GUI.ACTION.A) || actions.contains(GUI.ACTION.D))) {
            player2Controller.update(gameManager, Set.of(GUI.ACTION.S), updateTime);
        }
        for (GUI.ACTION action : actions) {
            if (action == GUI.ACTION.UP || action == GUI.ACTION.RIGHT || action == GUI.ACTION.DOWN || action == GUI.ACTION.LEFT) {
                player1Controller.update(gameManager, Set.of(action), updateTime);
            }
            if (action == GUI.ACTION.W || action == GUI.ACTION.D || action == GUI.ACTION.S || action == GUI.ACTION.A) {
                player2Controller.update(gameManager, Set.of(action), updateTime);
            }
        }
        if (checkDeadPlayers(gameManager)) {
            // TODO: restart level instead of going to menu
            gameManager.setCurrentScene(new Menu());
            return;
        }
        monsterController.update(gameManager, actions, updateTime);
        if (checkDeadPlayers(gameManager)) {
            // Also check for dead players after moving them and monsters
            gameManager.setCurrentScene(new Menu());
        }
    }
}
package controller.game;

import game.GameManager;
import model.elements.movingelements.Monster;
import model.Position;
import model.scenes.Level;
import controller.Controller;
import gui.GUI;

import java.io.IOException;
import java.util.Random;
import java.util.Set;

public class MonsterController extends Controller<Level> {
    private long lastUpdateTime;

    public MonsterController(Level level) {
        super(level);

        this.lastUpdateTime = 0;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    private void moveMonster(Monster monster, Position position) {
        if (getModel().isPositionFree(position)) {
            monster.setPosition(position);
        }
    }

    @Override
    public void update(GameManager gameManager, Set<GUI.ACTION> actions, long updateTime) throws IOException {
        if (updateTime - lastUpdateTime > 200) {
            Random rand = new Random();
            for (Monster monster : getModel().getMonsters()) {
                Position position = new Position(monster.getPosition().getX(),monster.getPosition().getY());
                int dx = rand.nextInt(-1,2);
                position.setX(position.getX() + dx);
                moveMonster(monster, position);
            }
            this.lastUpdateTime = updateTime;
        }
    }
}

package controller.game;

import game.GameManager;
import model.elements.dynamicelements.Monster;
import model.Position;
import model.scenes.Level;
import controller.Controller;
import gui.GUI;

import java.io.IOException;
import java.util.Set;

public class MonsterController extends Controller<Level> {
    public MonsterController(Level level) {
        super(level);
    }

    private void moveMonster(Monster monster, Position position) {
        int monsterSizeX = monster.getSizeX();
        int monsterSizeY = monster.getSizeY();
        for (int i = 0; i < monsterSizeX; i++) {
            for (int j = 0; j < monsterSizeY; j++) {
                if (! getModel().isPositionFree(new Position(position.getX() + i, position.getY() + j))) {
                    monster.setDirection(monster.getDirection() * -1);
                    return;
                }
            }
        }
        monster.setPosition(position);
    }

    @Override
    public void update(GameManager gameManager, Set<GUI.ACTION> actions, long updateTime) throws IOException {
        for (Monster monster : getModel().getMonsters()) {
            int posX = monster.getPosition().getX();
            int posY = monster.getPosition().getY();
            int desiredX = posX + monster.getDirection();
            if(desiredX == monster.getMaxX()){
                monster.setDirection(monster.getDirection() * -1);
                monster.setOrientation(Monster.ORIENTATION.LEFT);
                monster.setLastControlCount(0);
                desiredX = posX + monster.getDirection();
                moveMonster(monster, new Position(desiredX, posY));
            }else if(desiredX == monster.getMinX()){
                monster.setDirection(monster.getDirection() * -1);
                monster.setOrientation(Monster.ORIENTATION.RIGHT);
                monster.setLastControlCount(0);
                desiredX = posX + monster.getDirection();
                moveMonster(monster, new Position(desiredX, posY));
            }else{
                moveMonster(monster, new Position(desiredX, posY));
                monster.setLastControlCount(monster.getLastControlCount() + 1);
            }
        }
    }
}

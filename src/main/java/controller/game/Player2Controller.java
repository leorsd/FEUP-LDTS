package controller.game;

import Game.GameManager;
import Model.Position;
import Model.Scenes.Level;
import controller.Controller;
import GUI.GUI;

import java.util.Set;

public class Player2Controller extends Controller<Level> {
    public Player2Controller(Level level) {
        super(level);
    }

    public void movePlayer2Left() {
        Position desiredPosition = getModel().getPlayer2().getPosition();
        desiredPosition.setX(desiredPosition.getX() - 1);
        movePlayer(desiredPosition);
    }

    public void movePlayer2Right() {
        Position desiredPosition = getModel().getPlayer2().getPosition();
        desiredPosition.setX(desiredPosition.getX() + 1);
        movePlayer(desiredPosition);
    }

    public void movePlayer2Up() {
        Position desiredPosition = getModel().getPlayer2().getPosition();
        desiredPosition.setY(desiredPosition.getY() - 1);
        movePlayer(desiredPosition);
    }

    public void movePlayer2Down() {
        Position desiredPosition = getModel().getPlayer2().getPosition();
        desiredPosition.setY(desiredPosition.getY() + 1);
        movePlayer(desiredPosition);
    }

    private void movePlayer(Position position) {
        int playerSizeX = getModel().getPlayer2().getSizeX();
        int playerSizeY = getModel().getPlayer2().getSizeY();
        for (int i = 0; i < playerSizeX; i++) {
            for (int j = 0; j < playerSizeY; j++) {
                if (!getModel().isPositionFree(new Position(getModel().getPlayer2().getPosition().getX(),
                        getModel().getPlayer2().getPosition().getY() ))) return;
            }
        }
    }

    @Override
    public void update(GameManager gameManager, Set<GUI.ACTION> actions, long updateTime) {
        for (GUI.ACTION action : actions ) {
            if (action == GUI.ACTION.W) movePlayer2Up();
            if (action == GUI.ACTION.D) movePlayer2Right();
            if (action == GUI.ACTION.S) movePlayer2Down();
            if (action == GUI.ACTION.A) movePlayer2Left();
        }
    }
}

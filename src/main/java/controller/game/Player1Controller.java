package controller.game;

import Game.GameManager;
import Model.Position;
import Model.Scenes.Level;
import controller.Controller;
import GUI.GUI;

import java.util.Set;

public class Player1Controller extends Controller<Level> {
    public Player1Controller(Level level) {
        super(level);
    }

    public void movePlayer1Left() {
        Position desiredPosition = new Position(getModel().getPlayer1().getPosition().getX() - 1, getModel().getPlayer1().getPosition().getY());
        movePlayer(desiredPosition);
    }

    public void movePlayer1Right() {
        Position desiredPosition = new Position(getModel().getPlayer1().getPosition().getX() + 1, getModel().getPlayer1().getPosition().getY());
        movePlayer(desiredPosition);
    }

    public void movePlayer1Up() {
        Position desiredPosition = new Position(getModel().getPlayer1().getPosition().getX(), getModel().getPlayer1().getPosition().getY() - 1);
        movePlayer(desiredPosition);
    }

    public void movePlayer1Down() {
        Position desiredPosition = new Position(getModel().getPlayer1().getPosition().getX(), getModel().getPlayer1().getPosition().getY() + 1);
        movePlayer(desiredPosition);
    }

    private void movePlayer(Position position) {
        int playerSizeX = getModel().getPlayer1().getSizeX();
        int playerSizeY = getModel().getPlayer1().getSizeY();
        for (int i = 0; i < playerSizeX; i++) {
            for (int j = 0; j < playerSizeY; j++) {
                if (!(getModel().isPositionFree(new Position(position.getX() + i, position.getY() + j)))) return;
            }
        }
        getModel().getPlayer1().setPosition(position);
    }

    @Override
    public void update(GameManager gameManager, Set<GUI.ACTION> actions, long updateTime) {
        for (GUI.ACTION action : actions) {
            if (action == GUI.ACTION.UP) movePlayer1Up();
            if (action == GUI.ACTION.RIGHT) movePlayer1Right();
            if (action == GUI.ACTION.DOWN) movePlayer1Down();
            if (action == GUI.ACTION.LEFT) movePlayer1Left();
        }
    }
}

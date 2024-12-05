package controller.game;

import game.GameManager;
import model.Position;
import model.scenes.Level;
import controller.Controller;
import gui.GUI;

import java.util.Set;

public class Player1Controller extends Controller<Level> {
    private boolean isJumping = false;
    private int jumpHeight = 21;
    private int currentJumpHeight = 0;

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
        if(!isOnGround() && !isJumping) {
            Position desiredPosition = new Position(getModel().getPlayer1().getPosition().getX(), getModel().getPlayer1().getPosition().getY() + 1);
            movePlayer(desiredPosition);
        }else if(isOnGround()) {
            isJumping = false;
        }
    }

    private void movePlayer(Position position) {
        int playerSizeX = getModel().getPlayer1().getSizeX();
        int playerSizeY = getModel().getPlayer1().getSizeY();
        for (int i = 0; i < playerSizeX; i++) {
            for (int j = 0; j < playerSizeY; j++) {
                if (! getModel().isPositionFree(new Position(position.getX() + i, position.getY() + j))) return;
            }
        }
        getModel().getPlayer1().setPosition(position);
    }

    private boolean isOnGround() {
        int playerSizeX = getModel().getPlayer1().getSizeX();
        int playerSizeY = getModel().getPlayer1().getSizeY();
        boolean isOnGround = false;
        for(int i = 0; i < playerSizeX; i++) {
            Position belowPosition = new Position(getModel().getPlayer1().getPosition().getX()+i, getModel().getPlayer1().getPosition().getY() + playerSizeY);
            isOnGround |= !getModel().isPositionFree(belowPosition);
        }
        return isOnGround;
    }

    private boolean canJump() {
        int playerSizeX = getModel().getPlayer1().getSizeX();
        boolean canJump = true;
        for(int i = 0; i < playerSizeX; i++) {
            Position upPosition = new Position(getModel().getPlayer1().getPosition().getX() + i, getModel().getPlayer1().getPosition().getY() - 1);
            canJump &= getModel().isPositionFree(upPosition);
        }
        return canJump;
    }

    private void jump() {
        if(!isJumping && isOnGround()) {
            isJumping = true;
            currentJumpHeight = 0;
        }
    }

    @Override
    public void update(GameManager gameManager, Set<GUI.ACTION> actions, long updateTime) {
        for (GUI.ACTION action : actions) {
            if (action == GUI.ACTION.UP) jump();
            if (action == GUI.ACTION.RIGHT) movePlayer1Right();
            if (action == GUI.ACTION.DOWN) movePlayer1Down();
            if (action == GUI.ACTION.LEFT) movePlayer1Left();
        }
        if(isJumping){
            if(currentJumpHeight < jumpHeight && canJump()) {
                movePlayer1Up();
                currentJumpHeight++;
            }else{
                isJumping = false;
            }
        }
    }
}

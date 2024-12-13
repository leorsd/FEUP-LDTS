package controller.game;

import game.GameManager;
import model.Position;
import model.scenes.Level;
import controller.Controller;
import gui.GUI;

import java.util.Set;

public class Player2Controller extends Controller<Level> {
    private boolean isJumping = false;
    private int currentJumpHeight = 0;

    public Player2Controller(Level level) {
        super(level);
    }

    public void movePlayer2Left() {
        Position desiredPosition = new Position(getModel().getPlayer2().getPosition().getX() - 1, getModel().getPlayer2().getPosition().getY());
        movePlayer(desiredPosition);
    }

    public void movePlayer2Right() {
        Position desiredPosition = new Position(getModel().getPlayer2().getPosition().getX() + 1, getModel().getPlayer2().getPosition().getY());
        movePlayer(desiredPosition);
    }

    public void movePlayer2Up() {
        Position desiredPosition = new Position(getModel().getPlayer2().getPosition().getX(), getModel().getPlayer2().getPosition().getY() - 1);
        movePlayer(desiredPosition);
    }

    public void movePlayer2Down() {
        if(!isOnGround() && !isJumping) {
            Position desiredPosition = new Position(getModel().getPlayer2().getPosition().getX(), getModel().getPlayer2().getPosition().getY() + 1);
            movePlayer(desiredPosition);
        }
    }

    private void movePlayer(Position position) {
        int playerSizeX = getModel().getPlayer2().getSizeX();
        int playerSizeY = getModel().getPlayer2().getSizeY();
        for (int i = 0; i < playerSizeX; i++) {
            for (int j = 0; j < playerSizeY; j++) {
                if (!getModel().isPositionFree(new Position(position.getX() + i, position.getY() + j ))) return;
            }
        }
        getModel().getPlayer2().setPosition(position);
    }

    private boolean isOnGround() {
        int playerSizeX = getModel().getPlayer2().getSizeX();
        int playerSizeY = getModel().getPlayer2().getSizeY();
        boolean isOnGround = false;
        for(int i = 0; i < playerSizeX; i++) {
            Position belowPosition = new Position(getModel().getPlayer2().getPosition().getX()+i, getModel().getPlayer2().getPosition().getY() + playerSizeY);
            isOnGround |= !getModel().isPositionFree(belowPosition);
        }
        return isOnGround;
    }

    private boolean canJump() {
        int playerSizeX = getModel().getPlayer2().getSizeX();
        boolean canJump = true;
        for(int i = 0; i < playerSizeX; i++) {
            Position upPosition = new Position(getModel().getPlayer2().getPosition().getX() + i, getModel().getPlayer2().getPosition().getY() - 1);
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

    public void setJumpingFalse () {
        isJumping = false;
    }

    @Override
    public void update(GameManager gameManager, Set<GUI.ACTION> actions, long updateTime) {
        if (actions.contains(GUI.ACTION.W)) jump();
        int playerSpeed = getModel().getPlayer2().getSpeed();
        for (GUI.ACTION action : actions) {
            if (action == GUI.ACTION.D){
                for (int i=0; i<playerSpeed; i++) {
                    movePlayer2Right();
                }
            }
            if (action == GUI.ACTION.S){
                for (int i = 0; i < playerSpeed; i++) {
                    movePlayer2Down();
                }
            }
            if (action == GUI.ACTION.A){
                for (int i = 0; i<playerSpeed; i++) {
                    movePlayer2Left();
                }
            }
        }
        if(isJumping){
            int maxJumpHeight=getModel().getPlayer2().getMaxJumpHeight();
            for (int i = 0; i < playerSpeed; i++) {
                if (currentJumpHeight < maxJumpHeight && canJump()) {
                    movePlayer2Up();
                    currentJumpHeight++;
                } else {
                    isJumping = false;
                }
            }
        }
    }
}

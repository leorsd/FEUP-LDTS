package controller.game;

import game.GameManager;
import model.Position;
import model.elements.dynamicelements.Player;
import model.scenes.Level;
import controller.Controller;
import gui.GUI;

import java.util.Set;

public class Player1Controller extends Controller<Level> {
    private boolean isJumping = false;
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

    public boolean isOnGround() {
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
        if(isOnGround()) {
            isJumping = true;
            currentJumpHeight = 0;
        }
    }

    private void updatePlayerLastAction(Set<GUI.ACTION> actions) {
        if (isJumping && actions.contains(GUI.ACTION.RIGHT)) {
            if (getModel().getPlayer1().getOrientation() == Player.ORIENTATION.UPRIGHT) {
                getModel().getPlayer1().setLastActionCount(getModel().getPlayer1().getLastActionCount() + 1);
            } else {
                getModel().getPlayer1().setOrientation(Player.ORIENTATION.UPRIGHT);
                getModel().getPlayer1().setLastActionCount(0);
            }
        } else if (isJumping && actions.contains(GUI.ACTION.LEFT)) {
            if (getModel().getPlayer1().getOrientation() == Player.ORIENTATION.UPLEFT) {
                getModel().getPlayer1().setLastActionCount(getModel().getPlayer1().getLastActionCount() + 1);
            } else {
                getModel().getPlayer1().setOrientation(Player.ORIENTATION.UPLEFT);
                getModel().getPlayer1().setLastActionCount(0);
            }
        } else if (isJumping) {
            if (getModel().getPlayer1().getOrientation() == Player.ORIENTATION.UP) {
                getModel().getPlayer1().setLastActionCount(getModel().getPlayer1().getLastActionCount() + 1);
            } else {
                getModel().getPlayer1().setOrientation(Player.ORIENTATION.UP);
                getModel().getPlayer1().setLastActionCount(0);
            }
        } else if (actions.contains(GUI.ACTION.UP) && actions.contains(GUI.ACTION.RIGHT)) {
            if (getModel().getPlayer1().getOrientation() == Player.ORIENTATION.UPRIGHT) {
                getModel().getPlayer1().setLastActionCount(getModel().getPlayer1().getLastActionCount() + 1);
            } else {
                getModel().getPlayer1().setOrientation(Player.ORIENTATION.UPRIGHT);
                getModel().getPlayer1().setLastActionCount(0);
            }
        } else if (actions.contains(GUI.ACTION.UP) && actions.contains(GUI.ACTION.LEFT)) {
            if (getModel().getPlayer1().getOrientation() == Player.ORIENTATION.UPLEFT) {
                getModel().getPlayer1().setLastActionCount(getModel().getPlayer1().getLastActionCount() + 1);
            } else {
                getModel().getPlayer1().setOrientation(Player.ORIENTATION.UPLEFT);
                getModel().getPlayer1().setLastActionCount(0);
            }
        }  else if (actions.contains(GUI.ACTION.UP)) {
            if (getModel().getPlayer1().getOrientation() == Player.ORIENTATION.UP) {
                getModel().getPlayer1().setLastActionCount(getModel().getPlayer1().getLastActionCount() + 1);
            } else {
                getModel().getPlayer1().setOrientation(Player.ORIENTATION.UP);
                getModel().getPlayer1().setLastActionCount(0);
            }
        } else if (actions.contains(GUI.ACTION.RIGHT) && actions.contains(GUI.ACTION.DOWN)) {
            if (getModel().getPlayer1().getOrientation() == Player.ORIENTATION.DOWNRIGHT) {
                getModel().getPlayer1().setLastActionCount(getModel().getPlayer1().getLastActionCount() + 1);
            } else {
                getModel().getPlayer1().setOrientation(Player.ORIENTATION.DOWNRIGHT);
                getModel().getPlayer1().setLastActionCount(0);
            }
        } else if (actions.contains(GUI.ACTION.LEFT) && actions.contains(GUI.ACTION.DOWN)) {
            if (getModel().getPlayer1().getOrientation() == Player.ORIENTATION.DOWNLEFT) {
                getModel().getPlayer1().setLastActionCount(getModel().getPlayer1().getLastActionCount() + 1);
            } else {
                getModel().getPlayer1().setOrientation(Player.ORIENTATION.DOWNLEFT);
                getModel().getPlayer1().setLastActionCount(0);
            }
        } else if (actions.contains(GUI.ACTION.DOWN)) {
            if (getModel().getPlayer1().getOrientation() == Player.ORIENTATION.DOWN) {
                getModel().getPlayer1().setLastActionCount(getModel().getPlayer1().getLastActionCount() + 1);
            } else {
                getModel().getPlayer1().setOrientation(Player.ORIENTATION.DOWN);
                getModel().getPlayer1().setLastActionCount(0);
            }
        } else if (actions.contains(GUI.ACTION.RIGHT)) {
            if (getModel().getPlayer1().getOrientation() == Player.ORIENTATION.RIGHT) {
                getModel().getPlayer1().setLastActionCount(getModel().getPlayer1().getLastActionCount() + 1);
            } else {
                getModel().getPlayer1().setOrientation(Player.ORIENTATION.RIGHT);
                getModel().getPlayer1().setLastActionCount(0);
            }
        } else if (actions.contains(GUI.ACTION.LEFT)) {
            if (getModel().getPlayer1().getOrientation() == Player.ORIENTATION.LEFT) {
                getModel().getPlayer1().setLastActionCount(getModel().getPlayer1().getLastActionCount() + 1);
            } else {
                getModel().getPlayer1().setOrientation(Player.ORIENTATION.LEFT);
                getModel().getPlayer1().setLastActionCount(0);
            }
        } else {
            if (getModel().getPlayer1().getOrientation() == Player.ORIENTATION.STANDING) {
                getModel().getPlayer1().setLastActionCount(getModel().getPlayer1().getLastActionCount() + 1);
            } else {
                getModel().getPlayer1().setOrientation(Player.ORIENTATION.STANDING);
                getModel().getPlayer1().setLastActionCount(0);
            }
        }
    }

    public void setJumping (boolean jumping) {
        isJumping = jumping;
    }

    @Override
    public void update(GameManager gameManager, Set<GUI.ACTION> actions, long updateTime) {
        if (actions.contains(GUI.ACTION.UP)) jump();
        int playerSpeed = getModel().getPlayer1().getSpeed();
        for (GUI.ACTION action : actions) {
            if (action == GUI.ACTION.RIGHT){
                for (int i = 0; i < playerSpeed; i++) {
                    movePlayer1Right();
                }
            }
            if (action == GUI.ACTION.DOWN){
                for (int i = 0; i < playerSpeed; i++) {
                    movePlayer1Down();
                }
            }
            if (action == GUI.ACTION.LEFT){
                for (int i = 0; i < playerSpeed; i++) {
                    movePlayer1Left();
                }
            }
        }
        if(isJumping){
            int maxJumpHeight=getModel().getPlayer1().getMaxJumpHeight();
            for (int i = 0; i<playerSpeed; i++) {
                if (currentJumpHeight < maxJumpHeight && canJump()) {
                    movePlayer1Up();
                    currentJumpHeight++;
                } else {
                    isJumping = false;
                    break;
                }
            }
        }
        updatePlayerLastAction(actions);
    }
}

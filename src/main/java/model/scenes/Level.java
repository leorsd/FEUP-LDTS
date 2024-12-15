package model.scenes;

import model.elements.*;
import model.elements.movingelements.Monster;
import model.elements.movingelements.Player;
import model.Position;

import java.awt.image.BufferedImage;
import java.util.List;

public class Level implements Scene{
    private Wall levelEndingWall;
    private List<Wall> walls;
    private List<ToggleableWall> toggleableWalls;
    private List<Button> buttons;
    private List<Monster> monsters;
    private List<Trap> traps;
    private List<Key> keys;
    private Player player1;
    private Player player2;
    private int xBoundary;
    private int yBoundary;
    private BufferedImage background;
    private Position player1SpawnPosition;
    private Position player2SpawnPosition;
    private String nextLevel;

    public Level(List<Wall> walls, List<ToggleableWall> toggleableWalls, List<Button> buttons, List<Monster> monsters, List<Trap> traps, List<Key> keys, Player player1, Player player2, int xBoundary, int yBoundary, BufferedImage background, Wall levelEndingWall, String nextLevel) {
        this.walls = walls;
        this.toggleableWalls = toggleableWalls;
        this.buttons = buttons;
        this.monsters = monsters;
        this.traps = traps;
        this.keys = keys;
        this.player1 = player1;
        this.player2 = player2;
        this.xBoundary = xBoundary;
        this.yBoundary = yBoundary;
        this.background = background;
        this.levelEndingWall = levelEndingWall;
        this.player1SpawnPosition = player1.getPosition();
        this.player2SpawnPosition = player2.getPosition();
        this.nextLevel = nextLevel;
    }

    public String getNextLevel() {
        return nextLevel;
    }

    public void setNextLevel(String nextLevel) {
        this.nextLevel = nextLevel;
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public List<Trap> getTraps() {
        return traps;
    }

    public List<Key> getKeys() {
        return keys;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public List<ToggleableWall> getToggleableWalls() {
        return toggleableWalls;
    }

    public List<Button> getButtons() {
        return buttons;
    }

    public int getxBoundary() {
        return xBoundary;
    }

    public int getyBoundary() {
        return yBoundary;
    }

    public BufferedImage getBackground() {
        return background;
    }

    public Wall getLevelEndingWall() {
        return levelEndingWall;
    }

    public void setLevelEndingWall(Wall levelEndingWall) {
        this.levelEndingWall = levelEndingWall;
    }

    public boolean isPositionFree(Position position) {
        if (position.getX() < 0 || position.getX() >= xBoundary) {
            return false;
        }
        if (position.getY() < 0 || position.getY() >= yBoundary) {
            return false;
        }
        for (Wall wall : walls) {
            if (wall.hasCollided(position, 1, 1)) {
                return false;
            }
        }
        for (ToggleableWall wall : toggleableWalls) {
            if (wall.isActive() && wall.hasCollided(position, 1, 1)) {
                return false;
            }
        }
        return true;
    }

    public Position getPlayerSpawnPosition(Player player) {
        if (player.equals(player1)) {
            return player1SpawnPosition;
        }
        else {
            return player2SpawnPosition;
        }
    }

}

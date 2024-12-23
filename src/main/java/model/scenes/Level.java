package model.scenes;

import model.elements.dynamicelements.Door;
import model.elements.dynamicelements.Monster;
import model.elements.dynamicelements.Player;
import model.Position;
import model.elements.staticelements.*;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Objects;

public class Level implements Scene{
    private Door levelEndingDoor;
    private final List<Wall> walls;
    private final List<ToggleableWall> toggleableWalls;
    private final List<Button> buttons;
    private final List<Monster> monsters;
    private final List<Trap> traps;
    private final List<Key> keys;
    private final Player player1;
    private final Player player2;
    private final int xBoundary;
    private final int yBoundary;
    private final BufferedImage background;
    private Position player1SpawnPosition;
    private Position player2SpawnPosition;
    private String nextLevel;

    public Level(List<Wall> walls, List<ToggleableWall> toggleableWalls, List<Button> buttons, List<Monster> monsters, List<Trap> traps, List<Key> keys, Player player1, Player player2, int xBoundary, int yBoundary, BufferedImage background, Door levelEndingWall, String nextLevel) {
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
        this.levelEndingDoor = levelEndingWall;
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

    public Door getLevelEndingDoor() {
        return levelEndingDoor;
    }

    public void setLevelEndingDoor(Door levelEndingDoor) {
        this.levelEndingDoor = levelEndingDoor;
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

    public void setPlayerSpawnPosition(Player player, Position playerSpawnPosition) {
        if (player.equals(player1)) {
            this.player1SpawnPosition = playerSpawnPosition;
        } else {
            this.player2SpawnPosition = playerSpawnPosition;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Level level)) return false;
        return xBoundary == level.xBoundary && yBoundary == level.yBoundary && Objects.equals(levelEndingDoor, level.levelEndingDoor) && Objects.equals(walls, level.walls) && Objects.equals(toggleableWalls, level.toggleableWalls) && Objects.equals(buttons, level.buttons) && Objects.equals(monsters, level.monsters) && Objects.equals(traps, level.traps) && Objects.equals(keys, level.keys) && Objects.equals(player1, level.player1) && Objects.equals(player2, level.player2) && Objects.equals(background, level.background) && Objects.equals(player1SpawnPosition, level.player1SpawnPosition) && Objects.equals(player2SpawnPosition, level.player2SpawnPosition) && Objects.equals(nextLevel, level.nextLevel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(levelEndingDoor, walls, toggleableWalls, buttons, monsters, traps, keys, player1, player2, xBoundary, yBoundary, background, player1SpawnPosition, player2SpawnPosition, nextLevel);
    }
}

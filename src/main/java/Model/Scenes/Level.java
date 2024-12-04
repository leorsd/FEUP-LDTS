package Model.Scenes;

import Model.Elements.Characters.Monster;
import Model.Elements.Characters.Player;
import Model.Elements.Key;
import Model.Elements.Trap;
import Model.Elements.Wall;
import Model.Position;

import java.util.List;

public class Level implements Scene{
    private List<Wall> walls;
    private List<Monster> monsters;
    private List<Trap> traps;
    private List<Key> keys;
    private Player player1;
    private Player player2;
    private int xBoundary;
    private int yBoundary;

    public Level(List<Wall> walls, List<Monster> monsters, List<Trap> traps, List<Key> keys, Player player1, Player player2, int xBoundary, int yBoundary) {
        this.walls = walls;
        this.monsters = monsters;
        this.traps = traps;
        this.keys = keys;
        this.player1 = player1;
        this.player2 = player2;
        this.xBoundary = xBoundary;
        this.yBoundary = yBoundary;
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

    public int getxBoundary() {
        return xBoundary;
    }

    public int getyBoundary() {
        return yBoundary;
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
        return true;
    }
}

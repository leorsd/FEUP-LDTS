package Model;

import java.util.List;

public class Level implements Scene{
    private List<Wall> walls;
    private List<Monster> monsters;
    private List<Trap> traps;
    private Player player1;
    private Player player2;
    private int xBoundary;
    private int yBoundary;
    private LevelTutorial levelTutorial;

    public Level(List<Wall> walls, List<Monster> monsters, List<Trap> traps, Player player1, Player player2, int xBoundary, int yBoundary, LevelTutorial levelTutorial) {
        this.walls = walls;
        this.monsters = monsters;
        this.traps = traps;
        this.player1 = player1;
        this.player2 = player2;
        this.xBoundary = xBoundary;
        this.yBoundary = yBoundary;
        this.levelTutorial = levelTutorial;
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

    boolean isPositionFree(Position position) {
        if (position.getX() < 0 || position.getX() >= xBoundary) {
            return false;
        }
        if (position.getY() < 0 || position.getY() >= yBoundary) {
            return false;
        }
        for (Wall wall : walls) {
            if (wall.getPosition().equals(position)) {
                return false;
            }
        }
        return true;
    }
}

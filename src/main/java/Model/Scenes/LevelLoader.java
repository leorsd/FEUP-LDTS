package Model.Scenes;

import Model.Elements.MovingElements.Monster;
import Model.Elements.MovingElements.Player;
import Model.Elements.Key;
import Model.Elements.Trap;
import Model.Elements.Wall;
import Model.Position;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import com.google.common.base.Splitter;

import static com.google.common.base.Charsets.UTF_8;

public class LevelLoader {
    Integer xBoundary = null;
    Integer yBoundary = null;
    BufferedImage bi = null;

    Player player1;
    Player player2;

    List<Wall> walls = new ArrayList<>();
    List<Key> keys = new ArrayList<>();
    List<Monster> monsters = new ArrayList<>();
    List<Trap> traps = new ArrayList<>();

    private void readLevelSize(BufferedReader br) throws IOException {
        String line = br.readLine();

        if (line == null) {
            throw new IOException("Error while trying to read level size");
        }

        List<String> parts = Splitter.on(',').splitToList(line);

        if (parts.size() != 3) {
            throw new IOException("Level size needs to be like: x,y,imagePath");
        } else {
            xBoundary = Integer.parseInt(parts.getFirst());
            yBoundary = Integer.parseInt(parts.get(1));
            bi = ImageIO.read(new File(parts.get(2)));
        }
    }

    private void readPlayer1(BufferedReader br) throws IOException {
        String line = br.readLine();

        if (line == null) {
            throw new IOException("Error trying to read player1's information");
        }

        List<String> parts = Splitter.on(',').splitToList(line);

        if (parts.size() != 5) {
            throw new IOException("Player 1 specification needs to be like: x,y,sizeX,sizeY,imagePath");
        } else {
            player1 = new Player("Lavena", Integer.parseInt(parts.get(2)), Integer.parseInt(parts.get(3)),
                    new Position(Integer.parseInt(parts.get(0)), Integer.parseInt(parts.get(1))), ImageIO.read(new File(parts.get(4))));
        }
    }

    private void readPlayer2(BufferedReader br) throws IOException {
        String line = br.readLine();

        if (line == null) {
            throw new IOException("Error trying to read player2's information");
        }

        List<String> parts = Splitter.on(',').splitToList(line);

        if (parts.size() != 5) {
            throw new IOException("Player 2 specification needs to be like: x,y,sizeX,sizeY,imagePath");
        } else {
            player2 = new Player("Tergon", Integer.parseInt(parts.get(2)), Integer.parseInt(parts.get(3)),
                    new Position(Integer.parseInt(parts.get(0)), Integer.parseInt(parts.get(1))), ImageIO.read(new File(parts.get(4))));
        }
    }

    private void readKey(String line) throws IOException {
        List<String> parts = Splitter.on(',').splitToList(line);
        if (parts.size() != 5) {
            throw new IOException("Key specification needs to be like: x,y,sizeX,sizeY,imagePath");
        }
        this.keys.add(new Key(new Position(Integer.parseInt(parts.get(0)), Integer.parseInt(parts.get(1))), ImageIO.read(new File(parts.get(4))), Integer.parseInt(parts.get(2)), Integer.parseInt(parts.get(3))));
    }

    private void readMonster(String line) throws IOException {
        List<String> parts = Splitter.on(',').splitToList(line);
        if (parts.size() != 6) {
            throw new IOException("Monster specification needs to be like: health,x,y,sizeX,sizeY,imagePath");
        }
        this.monsters.add(new Monster(Integer.parseInt(parts.get(0)), new Position(Integer.parseInt(parts.get(1)), Integer.parseInt(parts.get(2))), ImageIO.read(new File(parts.get(5))),   Integer.parseInt(parts.get(3)), Integer.parseInt(parts.get(4))));
    }

    private void readTrap(String line) throws IOException {
        List<String> parts = Splitter.on(',').splitToList(line);
        if (parts.size() != 6) {
            throw new IOException("Trap specification needs to be like: target,x,y,sizeX,sizeY,imagePath");
        }
        this.traps.add(new Trap(parts.get(0), new Position(Integer.parseInt(parts.get(1)), Integer.parseInt(parts.get(2))), ImageIO.read(new File(parts.get(5))), Integer.parseInt(parts.get(3)), Integer.parseInt(parts.get(4))));
    }

    private void readWall(String line) throws IOException {
        List<String> parts = Splitter.on(',').splitToList(line);
        if (parts.size() != 5) {
            throw new IOException("Wall specification needs to be like: x,y,sizeX,sizeY,imagePath");
        }
        this.walls.add(new Wall(new Position(Integer.parseInt(parts.get(0)), Integer.parseInt(parts.get(1))), ImageIO.read(new File(parts.get(4))), Integer.parseInt(parts.get(2)), Integer.parseInt(parts.get(3))));
    }

    public Level loadLevel(String levelName) {
        BufferedReader br;
        try {
            br  = Files.newBufferedReader(Paths.get(levelName), UTF_8);
            readLevelSize(br);
            br.readLine(); // Read empty line
            readPlayer1(br);
            br.readLine(); // Read empty line
            readPlayer2(br);
            br.readLine(); // Read empty line

            while (true) {
                String line = br.readLine();
                if (line == null) {
                    throw new IOException("Error while trying to read Key");
                } else if (line.isEmpty()) {
                    break;
                }
                readKey(line);
            }

            while (true) {
                String line = br.readLine();
                if (line == null) {
                    throw new IOException("Error while trying to read Monster");
                } else if (line.isEmpty()) {
                    break;
                }
                readMonster(line);
            }

            while (true) {
                String line = br.readLine();
                if (line == null) {
                    throw new IOException("Error while trying to read Trap");
                } else if (line.isEmpty()) {
                    break;
                }
                readTrap(line);
            }

            while (true) {
                String line = br.readLine();
                if (line == null || line.isEmpty()) {
                    break;
                }
                readWall(line);
            }
        } catch (IOException e) {
            System.out.println("Error while trying to load level");
        }
        return new Level(walls, this.monsters, this.traps,this.keys, this.player1, this.player2, this.xBoundary, this.yBoundary);
    }
}

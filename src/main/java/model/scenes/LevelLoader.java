package model.scenes;

import model.elements.movingelements.Monster;
import model.elements.movingelements.Player;
import model.elements.Key;
import model.elements.Trap;
import model.elements.Wall;
import model.Position;

import javax.imageio.ImageIO;
import java.awt.*;
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
    BufferedImage wallBackground = null;
    BufferedImage background = null;

    Player player1;
    Player player2;

    List<Wall> walls = new ArrayList<>();
    List<Key> keys = new ArrayList<>();
    List<Monster> monsters = new ArrayList<>();
    List<Trap> traps = new ArrayList<>();

    private BufferedImage cropFromWallBackgroundImage(int xPos, int yPos, int width, int height) {
        return wallBackground.getSubimage(xPos, yPos, width, height);
    }

    public static BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }

    private void readLevelSize(BufferedReader br) throws IOException {
        String line = br.readLine();

        if (line == null) {
            throw new IOException("Error while trying to read level size");
        }

        List<String> parts = Splitter.on(',').splitToList(line);

        if (parts.size() != 4) {
            throw new IOException("Level size needs to be like: x,y,imagePath");
        } else {
            xBoundary = Integer.parseInt(parts.getFirst());
            yBoundary = Integer.parseInt(parts.get(1));
            wallBackground = ImageIO.read(new File(parts.get(2)));
            background = ImageIO.read(new File(parts.get(3)));
            wallBackground = resizeImage(wallBackground,xBoundary,yBoundary);
            background = resizeImage(background,xBoundary,yBoundary);
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
        if (parts.size() != 4) {
            throw new IOException("Wall specification needs to be like: x,y,sizeX,sizeY,imagePath");
        }
        int xPos = Integer.parseInt(parts.getFirst());
        int yPos = Integer.parseInt(parts.get(1));
        int width = Integer.parseInt(parts.get(2));
        int height = Integer.parseInt(parts.get(3));
        this.walls.add(new Wall(new Position(xPos,yPos), cropFromWallBackgroundImage(xPos,yPos,width,height), width, height));
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
        return new Level(walls, this.monsters, this.traps,this.keys, this.player1, this.player2, this.xBoundary, this.yBoundary, this.background);
    }
}

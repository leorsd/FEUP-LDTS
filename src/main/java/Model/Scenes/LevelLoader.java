package Model.Scenes;

import Model.Elements.Characters.Monster;
import Model.Elements.Characters.Player;
import Model.Elements.Key;
import Model.Elements.Trap;
import Model.Elements.Wall;
import Model.Position;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

    private BufferedImage cropImage(int xPos, int yPos, int width, int height) {
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

        String[] parts = line.split(",");

        if (parts.length != 4) {
            throw new IOException("Level size needs to be like: x,y,imagePath");
        } else {
            xBoundary = Integer.parseInt(parts[0]);
            yBoundary = Integer.parseInt(parts[1]);
            wallBackground = ImageIO.read(new File(parts[2]));
            background = ImageIO.read(new File(parts[3]));
            wallBackground = resizeImage(wallBackground,xBoundary,yBoundary);
            background = resizeImage(background,xBoundary,yBoundary);
        }
    }

    private void readPlayer1(BufferedReader br) throws IOException {
        String line = br.readLine();

        if (line == null) {
            throw new IOException("Error trying to read player1's information");
        }

        String[] parts = line.split(",");

        if (parts.length != 5) {
            throw new IOException("Player 1 specification needs to be like: x,y,sizeX,sizeY,imagePath");
        } else {
            player1 = new Player("Lavena", Integer.parseInt(parts[2]), Integer.parseInt(parts[3]),
                    new Position(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])), ImageIO.read(new File(parts[4])));
        }
    }

    private void readPlayer2(BufferedReader br) throws IOException {
        String line = br.readLine();

        if (line == null) {
            throw new IOException("Error trying to read player2's information");
        }

        String[] parts = line.split(",");

        if (parts.length != 5) {
            throw new IOException("Player 2 specification needs to be like: x,y,sizeX,sizeY,imagePath");
        } else {
            player2 = new Player("Tergon", Integer.parseInt(parts[2]), Integer.parseInt(parts[3]),
                    new Position(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])), ImageIO.read(new File(parts[4])));
        }
    }

    private void readKey(String line) throws IOException {
        String[] parts = line.split(",");
        if (parts.length != 5) {
            throw new IOException("Key specification needs to be like: x,y,sizeX,sizeY,imagePath");
        }
        this.keys.add(new Key(new Position(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])), ImageIO.read(new File(parts[4])), Integer.parseInt(parts[2]), Integer.parseInt(parts[3])));
    }

    private void readMonster(String line) throws IOException {
        String[] parts = line.split(",");
        if (parts.length != 6) {
            throw new IOException("Monster specification needs to be like: health,x,y,sizeX,sizeY,imagePath");
        }
        this.monsters.add(new Monster(Integer.parseInt(parts[0]), new Position(Integer.parseInt(parts[1]), Integer.parseInt(parts[2])), ImageIO.read(new File(parts[5])),   Integer.parseInt(parts[3]), Integer.parseInt(parts[4])));
    }

    private void readTrap(String line) throws IOException {
        String[] parts = line.split(",");
        if (parts.length != 6) {
            throw new IOException("Trap specification needs to be like: target,x,y,sizeX,sizeY,imagePath");
        }
        this.traps.add(new Trap(parts[0], new Position(Integer.parseInt(parts[1]), Integer.parseInt(parts[2])), ImageIO.read(new File(parts[5])), Integer.parseInt(parts[3]), Integer.parseInt(parts[4])));
    }

    private void readWall(String line) throws IOException {
        String[] parts = line.split(",");
        if (parts.length != 4) {
            throw new IOException("Wall specification needs to be like: x,y,sizeX,sizeY");
        }
        int xPos = Integer.parseInt(parts[0]);
        int yPos = Integer.parseInt(parts[1]);
        int width = Integer.parseInt(parts[2]);
        int height = Integer.parseInt(parts[3]);
        this.walls.add(new Wall(new Position(xPos,yPos), cropImage(xPos,yPos,width,height), width, height));
    }

    public Level loadLevel(String levelName) {
        try {
            FileReader f = new FileReader(levelName);
        } catch (IOException e) {
            System.out.println("File not found");
        }

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(levelName));
            readLevelSize(br);
            String empty = br.readLine();
            readPlayer1(br);
            empty = br.readLine();
            readPlayer2(br);
            empty = br.readLine();

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

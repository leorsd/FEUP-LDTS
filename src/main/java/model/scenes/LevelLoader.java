package model.scenes;

import model.elements.dynamicelements.Door;
import model.elements.staticelements.ToggleableWall;
import model.elements.dynamicelements.Monster;
import model.elements.dynamicelements.Player;
import model.elements.staticelements.Key;
import model.elements.staticelements.Button;
import model.elements.staticelements.Trap;
import model.elements.staticelements.Wall;
import model.Position;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Splitter;

import static com.google.common.base.Charsets.UTF_8;

public class LevelLoader {
    Integer xBoundary = null;
    Integer yBoundary = null;

    BufferedImage wallBackground = null;
    BufferedImage background = null;

    Player player1;
    Player player2;

    Door levelTransitionDoor;
    List<Wall> walls = new ArrayList<>();
    List<ToggleableWall> toggleableWalls = new ArrayList<>();
    List<Button> buttons = new ArrayList<>();
    Map<Integer, ToggleableWall> toggleableWallsMap = new HashMap<>();
    List<Key> keys = new ArrayList<>();
    List<Monster> monsters = new ArrayList<>();
    List<Trap> traps = new ArrayList<>();

    String nextLevel;

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
            throw new IOException("Error while trying to read level configuration");
        }

        List<String> parts = Splitter.on(',').splitToList(line);

        if (parts.size() != 5) {
            throw new IOException("Level specification needs to be like: levelNumber,xBoundary,yBoundary,regularWallsImagePath,backgroundImagePath");
        } else {
            xBoundary = Integer.parseInt(parts.getFirst());
            yBoundary = Integer.parseInt(parts.get(1));
            wallBackground = ImageIO.read(new File(parts.get(2)));
            background = ImageIO.read(new File(parts.get(3)));
            nextLevel = parts.get(4);
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

        if (parts.size() != 6) {
            throw new IOException("Player 1 specification needs to be like: x,y,sizeX,sizeY,maxJumpHeight,speed");
        } else {
            player1 = new Player(
                    "Tergon",
                    Integer.parseInt(parts.get(2)),
                    Integer.parseInt(parts.get(3)),
                    new Position(Integer.parseInt(parts.get(0)), Integer.parseInt(parts.get(1))),
                    Integer.parseInt(parts.get(5)),
                    Integer.parseInt(parts.get(4))
            );
        }
    }

    private void readPlayer2(BufferedReader br) throws IOException {
        String line = br.readLine();

        if (line == null) {
            throw new IOException("Error trying to read player2's information");
        }

        List<String> parts = Splitter.on(',').splitToList(line);

        if (parts.size() != 6) {
            throw new IOException("Player 2 specification needs to be like: x,y,sizeX,sizeY,maxJumpHeight,speed");
        } else {
            player2 = new Player(
                    "Lavena",
                    Integer.parseInt(parts.get(2)),
                    Integer.parseInt(parts.get(3)),
                    new Position(Integer.parseInt(parts.get(0)), Integer.parseInt(parts.get(1))),
                    Integer.parseInt(parts.get(5)),
                    Integer.parseInt(parts.get(4))
            );
        }
    }

    private void readKey(String line) throws IOException {
        List<String> parts = Splitter.on(',').splitToList(line);
        if (parts.size() != 6) {
            throw new IOException("Key specification needs to be like: target,x,y,sizeX,sizeY,imagePath");
        }
        this.keys.add(new Key(
                new Position(Integer.parseInt(parts.get(1)), Integer.parseInt(parts.get(2))),
                ImageIO.read(new File(parts.get(5))),
                Integer.parseInt(parts.get(3)),
                Integer.parseInt(parts.get(4)),
                parts.get(0)
        ));
    }

    private void readMonster(String line) throws IOException {
        List<String> parts = Splitter.on(',').splitToList(line);
        if (parts.size() != 6) {
            throw new IOException("Monster specification needs to be like: x,y,sizeX,sizeY,minX,maxX");
        }
        this.monsters.add(new Monster(
                new Position(Integer.parseInt(parts.get(0)), Integer.parseInt(parts.get(1))),
                Integer.parseInt(parts.get(2)),
                Integer.parseInt(parts.get(3)),
                Integer.parseInt(parts.get(4)),
                Integer.parseInt(parts.get(5))
        ));
    }

    private void readTrap(String line) throws IOException {
        List<String> parts = Splitter.on(',').splitToList(line);
        if (parts.size() != 6) {
            throw new IOException("Trap specification needs to be like: target,x,y,sizeX,sizeY,imagePath");
        }
        String target = parts.getFirst();
        int width = Integer.parseInt(parts.get(3));
        int height = Integer.parseInt(parts.get(4));
        BufferedImage trapBackground = ImageIO.read(new File(parts.get(5))).getSubimage(0,0, width, height);
        this.traps.add(new Trap(
                target,
                new Position(Integer.parseInt(parts.get(1)), Integer.parseInt(parts.get(2))),
                trapBackground,
                width,
                height
        ));
    }

    private void readWall(String line) throws IOException {
        List<String> parts = Splitter.on(',').splitToList(line);
        if (parts.size() != 4) {
            throw new IOException("Wall specification needs to be like: x,y,sizeX,sizeY");
        }
        int xPos = Integer.parseInt(parts.getFirst());
        int yPos = Integer.parseInt(parts.get(1));
        int width = Integer.parseInt(parts.get(2));
        int height = Integer.parseInt(parts.get(3));
        BufferedImage wallBackground2 = wallBackground.getSubimage(xPos, yPos, width, height);
        this.walls.add(new Wall(
                new Position(xPos,yPos),
                wallBackground2,
                width,
                height
        ));
    }

    private void readToggleableWall(String line) throws IOException {
        List<String> parts = Splitter.on(',').splitToList(line);
        if (parts.size() != 6) {
            throw new IOException("ToggleableWall specification needs to be like: id,x,y,sizeX,sizeY,imagePath");
        }
        Integer id = Integer.parseInt(parts.getFirst());
        int xPos = Integer.parseInt(parts.get(1));
        int yPos = Integer.parseInt(parts.get(2));
        int width = Integer.parseInt(parts.get(3));
        int height = Integer.parseInt(parts.get(4));
        BufferedImage wallBackground = ImageIO.read(new File(parts.get(5))).getSubimage(0,0, width, height);
        ToggleableWall toggleableWall = new ToggleableWall(new Position(xPos,yPos), wallBackground, width, height);
        toggleableWallsMap.put(id, toggleableWall);
        toggleableWalls.add(toggleableWall);
    }

    private void readButtons(String line) throws IOException {
        List<String> parts = Splitter.on(',').splitToList(line);
        if (parts.size() != 6) {
            throw new IOException("Button specification needs to be like: idToggleableWall,x,y,sizeX,sizeY,imagePath");
        }
        Integer id = Integer.parseInt(parts.get(0));
        int xPos = Integer.parseInt(parts.get(1));
        int yPos = Integer.parseInt(parts.get(2));
        int width = Integer.parseInt(parts.get(3));
        int height = Integer.parseInt(parts.get(4));
        BufferedImage wallBackground = ImageIO.read(new File(parts.get(5))).getSubimage(0,0, width, height);
        Button button = new Button(new Position(xPos, yPos), wallBackground, width, height, toggleableWallsMap.get(id));
        buttons.add(button);
    }


    private void readLevelTransitionWall(String line) throws IOException {
        List<String> parts = Splitter.on(',').splitToList(line);
        if (parts.size() != 4) {
            throw new IOException("Level Transition Door specification needs to be like: x,y,sizeX,sizeY");
        }
        int xPos = Integer.parseInt(parts.get(0));
        int yPos = Integer.parseInt(parts.get(1));
        int width = Integer.parseInt(parts.get(2));
        int height = Integer.parseInt(parts.get(3));
        this.levelTransitionDoor = new Door(
                new Position(xPos,yPos),
                width,
                height
        );
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

            String line = br.readLine();
            if (line == null || line.isEmpty()) {
                throw new IOException("Level lacks level transition door: going to next levels won't be possible");
            } else {
                readLevelTransitionWall(line);
            }
            line = br.readLine();
            while (true) {
                line = br.readLine();
                if (line == null || line.isEmpty()) {
                    break;
                }
                readToggleableWall(line);
            }

            while (true) {
                line = br.readLine();
                if (line == null || line.isEmpty()) {
                    break;
                }
                readButtons(line);
            }
        } catch (IOException e) {
            System.out.println("Error while trying to load level");
        }
        return new Level(this.walls, this.toggleableWalls, this.buttons, this.monsters, this.traps,this.keys, this.player1, this.player2, this.xBoundary, this.yBoundary, this.background, this.levelTransitionDoor,this.nextLevel);
    }
}

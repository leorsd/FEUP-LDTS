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
import java.util.*;
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

    Door levelEndingDoor;
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

    private void readLevelConfig(BufferedReader br) throws IOException {
        String line;
        try {
            line = br.readLine();
        } catch (IOException e) {
            throw new IOException("Could not read line for level config");
        }

        if (line == null) {
            throw new IOException("Null line caused error while trying to read level configuration");
        }

        List<String> parts = Splitter.on(',').splitToList(line);

        if (parts.size() != 5) {
            throw new IOException("Level config line needs to be like: xBoundary,yBoundary,regularWallsImagePath,backgroundImagePath,nextLevelPath");
        } else {
            try {
                xBoundary = Integer.parseInt(parts.getFirst());
            } catch (NumberFormatException e) {
                throw new IOException("Invalid xBoundary number when loading level");
            }
            try {
                yBoundary = Integer.parseInt(parts.get(1));
            } catch (NumberFormatException e) {
                throw new IOException("Invalid yBoundary number when loading level");
            }
            try {
                wallBackground = ImageIO.read(new File(parts.get(2)));
            } catch (IOException e) {
                throw new IOException("Could not read wall background image when loading level");
            }
            try {
                background = ImageIO.read(new File(parts.get(3)));
            } catch (IOException e) {
                throw new IOException("Could not read background image when loading level");
            }
            nextLevel = parts.get(4);
            wallBackground = resizeImage(wallBackground,xBoundary,yBoundary);
            background = resizeImage(background,xBoundary,yBoundary);
        }
    }

    private void readPlayer1(BufferedReader br) throws IOException {
        String line;
        try {
            line = br.readLine();
        } catch (IOException e) {
            throw new IOException("Could not read line for player 1 when loading level");
        }

        if (line == null) {
            throw new IOException("Null line caused error when trying to read player1's information");
        }

        List<String> parts = Splitter.on(',').splitToList(line);

        if (parts.size() != 6) {
            throw new IOException("Player 1 specification needs to be like: x,y,sizeX,sizeY,maxJumpHeight,speed");
        } else {
            int x;
            int y;
            int sizeX;
            int sizeY;
            int maxJumpHeight;
            int speed;
            try {
                x = Integer.parseInt(parts.getFirst());
            } catch (NumberFormatException e) {
                throw new IOException("Invalid x when trying to read player1's information when loading level");
            }
            try {
                y = Integer.parseInt(parts.get(1));
            } catch (NumberFormatException e) {
                throw new IOException("Invalid y when trying to read player1's information when loading level");
            }
            try {
                sizeX = Integer.parseInt(parts.get(2));
            } catch (NumberFormatException e) {
                throw new IOException("Invalid sizeX when trying to read player1's information when loading level");
            }
            try {
                sizeY = Integer.parseInt(parts.get(3));
            } catch (NumberFormatException e) {
                throw new IOException("Invalid sizeY when trying to read player1's information when loading level");
            }
            try {
                maxJumpHeight = Integer.parseInt(parts.get(4));
            } catch (NumberFormatException e) {
                throw new IOException("Invalid maxJumpHeight for player 1 when loading level");
            }
            try {
                speed = Integer.parseInt(parts.get(5));
            } catch (NumberFormatException e) {
                throw new IOException("Invalid speed for player 1 when loading level");
            }
            player1 = new Player(
                    "Tergon",
                    sizeX,
                    sizeY,
                    new Position(x, y),
                    speed,
                    maxJumpHeight
            );
        }
    }

    private void readPlayer2(BufferedReader br) throws IOException {
        String line;
        try {
            line = br.readLine();
        } catch (IOException e) {
            throw new IOException("Could not read line for player 2 when loading level");
        }

        if (line == null) {
            throw new IOException("Null line caused error when trying to read player2's information");
        }

        List<String> parts = Splitter.on(',').splitToList(line);

        if (parts.size() != 6) {
            throw new IOException("Player 2 specification needs to be like: x,y,sizeX,sizeY,maxJumpHeight,speed");
        } else {
            int x;
            int y;
            int sizeX;
            int sizeY;
            int maxJumpHeight;
            int speed;
            try {
                x = Integer.parseInt(parts.getFirst());
            } catch (NumberFormatException e) {
                throw new IOException("Invalid x when trying to read player2's information when loading level");
            }
            try {
                y = Integer.parseInt(parts.get(1));
            } catch (NumberFormatException e) {
                throw new IOException("Invalid y when trying to read player2's information when loading level");
            }
            try {
                sizeX = Integer.parseInt(parts.get(2));
            } catch (NumberFormatException e) {
                throw new IOException("Invalid sizeX when trying to read player2's information when loading level");
            }
            try {
                sizeY = Integer.parseInt(parts.get(3));
            } catch (NumberFormatException e) {
                throw new IOException("Invalid sizeY when trying to read player2's information when loading level");
            }
            try {
                maxJumpHeight = Integer.parseInt(parts.get(4));
            } catch (NumberFormatException e) {
                throw new IOException("Invalid maxJumpHeight for player 2 when loading level");
            }
            try {
                speed = Integer.parseInt(parts.get(5));
            } catch (NumberFormatException e) {
                throw new IOException("Invalid speed for player 2 when loading level");
            }
            player2 = new Player(
                    "Lavena",
                    sizeX,
                    sizeY,
                    new Position(x, y),
                    speed,
                    maxJumpHeight
            );
        }
    }

    private void readKey(String line) throws IOException {
        List<String> parts = Splitter.on(',').splitToList(line);
        if (parts.size() != 6) {
            throw new IOException("Key specification needs to be like: target,x,y,sizeX,sizeY,imagePath");
        }
        int X;
        int Y;
        BufferedImage image;
        int sizeX;
        int sizeY;
        try {
            X = Integer.parseInt(parts.get(1));
        } catch (NumberFormatException e) {
            throw new IOException("Invalid x when trying to read key when loading level");
        }
        try {
            Y = Integer.parseInt(parts.get(2));
        } catch (NumberFormatException e) {
            throw new IOException("Invalid y when trying to read key when loading level");
        }

        try {
            image = ImageIO.read(new File(parts.get(5)));
        } catch (IOException e) {
            throw new IOException("Could not read image for key when loading level");
        }

        try {
            sizeX = Integer.parseInt(parts.get(3));
        } catch (NumberFormatException e) {
            throw new IOException("Invalid sizeX when trying to read key when loading level");
        }

        try {
            sizeY = Integer.parseInt(parts.get(4));
        } catch (NumberFormatException e) {
            throw new IOException("Invalid sizeY when trying to read key when loading level");
        }

        this.keys.add(new Key(
                new Position(X, Y),
                image,
                sizeX,
                sizeY,
                parts.get(0)
        ));
    }

    private void readMonster(String line) throws IOException {
        List<String> parts = Splitter.on(',').splitToList(line);
        if (parts.size() != 6) {
            throw new IOException("Monster specification needs to be like: x,y,sizeX,sizeY,minX,maxX");
        }
        int X;
        int Y;
        int sizeX;
        int sizeY;
        int minX;
        int maxX;
        try {
            X = Integer.parseInt(parts.getFirst());
        } catch (NumberFormatException e) {
            throw new IOException("Invalid x when trying to read monster when loading level");
        }
        try {
            Y = Integer.parseInt(parts.get(1));
        } catch (NumberFormatException e) {
            throw new IOException("Invalid y when trying to read monster when loading level");
        }

        try {
            sizeX = Integer.parseInt(parts.get(2));
        } catch (NumberFormatException e) {
            throw new IOException("Invalid sizeX when trying to read key when loading level");
        }

        try {
            sizeY = Integer.parseInt(parts.get(3));
        } catch (NumberFormatException e) {
            throw new IOException("Invalid sizeY when trying to read key when loading level");
        }

        try {
            minX = Integer.parseInt(parts.get(4));
        } catch (NumberFormatException e) {
            throw new IOException("Invalid minX when trying to read key when loading level");
        }

        try {
            maxX = Integer.parseInt(parts.get(5));
        } catch (NumberFormatException e) {
            throw new IOException("Invalid maxX when trying to read key when loading level");
        }
        this.monsters.add(new Monster(
                new Position(X, Y),
                sizeX,
                sizeY,
                minX,
                maxX
        ));
    }

    private void readTrap(String line) throws IOException {
        List<String> parts = Splitter.on(',').splitToList(line);
        if (parts.size() != 6) {
            throw new IOException("Trap specification needs to be like: target,x,y,sizeX,sizeY,imagePath");
        }
        int X;
        int Y;
        BufferedImage image;
        int sizeX;
        int sizeY;
        try {
            X = Integer.parseInt(parts.get(1));
        } catch (NumberFormatException e) {
            throw new IOException("Invalid x when trying to read trap when loading level");
        }
        try {
            Y = Integer.parseInt(parts.get(2));
        } catch (NumberFormatException e) {
            throw new IOException("Invalid y when trying to read trap when loading level");
        }

        try {
            sizeX = Integer.parseInt(parts.get(3));
        } catch (NumberFormatException e) {
            throw new IOException("Invalid sizeX when trying to read trap when loading level");
        }

        try {
            sizeY = Integer.parseInt(parts.get(4));
        } catch (NumberFormatException e) {
            throw new IOException("Invalid sizeY when trying to read trap when loading level");
        }

        try {
            image = ImageIO.read(new File(parts.get(5))).getSubimage(0,0, sizeX, sizeY);
        } catch (IOException e) {
            throw new IOException("Could not read image for trap when loading level");
        }

        String target = parts.getFirst();

        this.traps.add(new Trap(
                target,
                new Position(X, Y),
                image,
                sizeX,
                sizeY
        ));
    }

    private void readWall(String line) throws IOException {
        List<String> parts = Splitter.on(',').splitToList(line);
        if (parts.size() != 4) {
            throw new IOException("Wall specification needs to be like: x,y,sizeX,sizeY");
        }
        int xPos;
        int yPos ;
        int width;
        int height;
        try {
            xPos = Integer.parseInt(parts.getFirst());
        } catch (NumberFormatException e) {
            throw new IOException("Invalid x when trying to read wall when loading level");
        }
        try {
            yPos = Integer.parseInt(parts.get(1));
        } catch (NumberFormatException e) {
            throw new IOException("Invalid y when trying to read wall when loading level");
        }
        try {
            width = Integer.parseInt(parts.get(2));
        } catch (NumberFormatException e) {
            throw new IOException("Invalid width when trying to read wall when loading level");
        }
        try {
            height = Integer.parseInt(parts.get(3));
        } catch (NumberFormatException e) {
            throw new IOException("Invalid height when trying to read wall when loading level");
        }
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
        Integer id;
        int width;
        int height;
        BufferedImage wallBackground;
        int xPos;
        int yPos;
        try {
            id = Integer.parseInt(parts.getFirst());
        } catch (NumberFormatException e) {
            throw new IOException("Invalid id when trying to read toggleable wall when loading level");
        }

        try {
            width = Integer.parseInt(parts.get(3));
        } catch (NumberFormatException e) {
            throw new IOException("Invalid width when trying to read toggleable wall when loading level");
        }
        try {
            height = Integer.parseInt(parts.get(4));
        } catch (NumberFormatException e) {
            throw new IOException("Invalid height when trying to read toggleable wall when loading level");
        }
        try {
            wallBackground = ImageIO.read(new File(parts.get(5))).getSubimage(0, 0, width, height);
        } catch (IOException e) {
            throw new IOException("Could not read image for toggleable wall when loading level");
        }
        try {
            xPos = Integer.parseInt(parts.get(1));
        } catch (NumberFormatException e) {
            throw new IOException("Invalid x when trying to read toggleable wall when loading level");
        }
        try {
             yPos = Integer.parseInt(parts.get(2));
        } catch (NumberFormatException e) {
            throw new IOException("Invalid y when trying to read toggleable wall when loading level");
        }
        ToggleableWall toggleableWall = new ToggleableWall(new Position(xPos,yPos), wallBackground, width, height);
        toggleableWallsMap.put(id, toggleableWall);
        toggleableWalls.add(toggleableWall);
    }

    private void readButtons(String line) throws IOException {
        List<String> parts = Splitter.on(',').splitToList(line);
        if (parts.size() != 6) {
            throw new IOException("Button specification needs to be like: idToggleableWall,x,y,sizeX,sizeY,imagePath");
        }
        Integer id;
        int xPos;
        int yPos;
        int width;
        int height;
        BufferedImage buttonImage;
        try {
            id = Integer.parseInt(parts.getFirst());
        } catch (NumberFormatException e) {
            throw new IOException("Invalid id when trying to read button when loading level");
        }
        try {
            xPos = Integer.parseInt(parts.get(1));
        } catch (NumberFormatException e) {
            throw new IOException("Invalid x when trying to read button when loading level");
        }
        try {
            yPos = Integer.parseInt(parts.get(2));
        } catch (NumberFormatException e) {
            throw new IOException("Invalid y when trying to read button when loading level");
        }
        try {
            width = Integer.parseInt(parts.get(3));
        } catch (NumberFormatException e) {
            throw new IOException("Invalid width when trying to read button when loading level");
        }
        try {
            height = Integer.parseInt(parts.get(4));
        } catch (NumberFormatException e) {
            throw new IOException("Invalid height when trying to read button when loading level");
        }

        try {
            buttonImage = ImageIO.read(new File(parts.get(5))).getSubimage(0,0, width, height);
        } catch (IOException e) {
            throw new IOException("Could not read image for button when loading level");
        }

        Button button = new Button(new Position(xPos, yPos), buttonImage, width, height, toggleableWallsMap.get(id));
        buttons.add(button);
    }


    private void readLevelTransitionWall(String line) throws IOException {
        List<String> parts = Splitter.on(',').splitToList(line);
        if (parts.size() != 4) {
            throw new IOException("Level Transition Door specification needs to be like: x,y,sizeX,sizeY");
        }
        int xPos;
        int yPos;
        int width;
        int height;
        try {
            xPos = Integer.parseInt(parts.getFirst());
        } catch (NumberFormatException e) {
            throw new IOException("Invalid x when trying to read levelTransitionWall when loading level");
        }
        try {
            yPos = Integer.parseInt(parts.get(1));
        } catch (NumberFormatException e) {
            throw new IOException("Invalid y when trying to read levelTransitionWall when loading level");
        }
        try {
            width = Integer.parseInt(parts.get(2));
        } catch (NumberFormatException e) {
            throw new IOException("Invalid width when trying to read levelTransitionWall when loading level");
        }
        try {
            height = Integer.parseInt(parts.get(3));
        } catch (NumberFormatException e) {
            throw new IOException("Invalid height when trying to read levelTransitionWall when loading level");
        }
        this.levelEndingDoor = new Door(
                new Position(xPos,yPos),
                width,
                height
        );
    }

    public Level loadLevel(String levelName) throws IOException {
        BufferedReader br;
        try {
            br = Files.newBufferedReader(Paths.get(levelName), UTF_8); // Open level configurations file
        } catch (IOException e) {
            throw new IOException("Error in LevelLoader when trying to open file: " + levelName);
        }

        readLevelConfig(br);

        try {
            br.readLine(); // Read empty line
        } catch (IOException e) {
            throw new IOException("Error when trying to read empty line between levelConfig and Player1's configs when loading level");
        }

        readPlayer1(br);

        try {
            br.readLine(); // Read empty line
        } catch (IOException e) {
            throw new IOException("Error when trying to read empty line between Player1 and Player2's configs when loading level");
        }

        readPlayer2(br);

        try {
            br.readLine(); // Read empty line
        } catch (IOException e) {
            throw new IOException("Error when trying to read empty line between Player2's configs and keys when loading level");
        }

        while (true) {
            String line;
            try {
                line = br.readLine();
            } catch (IOException e) {
                throw new IOException("Error when trying to read line with key config, or empty line between keys and monsters");
            }
            if (line == null) {
                throw new IOException("Error while trying to read Key");
            } else if (line.isEmpty()) {
                break;
            }
            readKey(line);
        }

        while (true) {
            String line;
            try {
                line = br.readLine();
            } catch (IOException e) {
                throw new IOException("Error when trying to read line with monster config, or empty line between monsters and traps");
            }
            if (line == null) {
                throw new IOException("Error while trying to read Monster");
            } else if (line.isEmpty()) {
                break;
            }
            readMonster(line);
        }

        while (true) {
            String line;
            try {
                line = br.readLine();
            } catch (IOException e) {
                throw new IOException("Error when trying to read line with trap config, or empty line between traps and walls");
            }
            if (line == null) {
                throw new IOException("Error while trying to read Trap");
            } else if (line.isEmpty()) {
                break;
            }
            readTrap(line);
        }

        while (true) {
            String line;
            try {
                line = br.readLine();
            } catch (IOException e) {
                throw new IOException("Error when trying to read line with wall config, or empty line between walls and levelTransitionDoor");
            }
            if (line == null || line.isEmpty()) {
                break;
            }
            readWall(line);
        }

        String line;
        try {
            line = br.readLine();
        } catch (IOException e) {
            throw new IOException("Error when trying to read line with levelTransitionDoor");
        }
        if (line == null || line.isEmpty()) {
            throw new IOException("Level lacks level transition door: going to next levels wouldn't be possible. Failing.");
        } else {
            readLevelTransitionWall(line);
        }


        try {
            br.readLine(); // Read empty line
        } catch (IOException e) {
            throw new IOException("Error when trying to read empty line between levelTransitionDoor and toggleableWalls when loading level");
        }

        while (true) {
            try {
                line = br.readLine();
            } catch (IOException e) {
                throw new IOException("Error when trying to read line with toggleableWall config, or empty line between toggleableWalls and buttons");
            }
            if (line == null || line.isEmpty()) {
                break;
            }
            readToggleableWall(line);
        }

        while (true) {
            try {
                line = br.readLine();
            } catch (IOException e) {
                throw new IOException("Error when trying to read line with button configs");
            }
            if (line == null || line.isEmpty()) {
                break;
            }
            readButtons(line);
        }

        return new Level(this.walls, this.toggleableWalls, this.buttons, this.monsters, this.traps,this.keys, this.player1, this.player2, this.xBoundary, this.yBoundary, this.background, this.levelEndingDoor,this.nextLevel);
    }
}

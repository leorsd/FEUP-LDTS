package gui;

import model.elements.movingelements.Monster;
import model.elements.movingelements.Player;
import model.elements.Key;
import model.elements.Trap;
import model.elements.Wall;
import model.Position;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Set;

public interface GUI {
    Set<ACTION> getNextAction() throws IOException;

    void drawPlayer(Player player);

    void drawWall(Wall wall);

    void drawMonster(Monster monster);

    void drawKey(Key key);

    void drawTrap(Trap trap);

    void drawImage(Position position, BufferedImage image);

    int getGUIWidth();

    int getGUIHeight();

    void clear();

    void refresh() throws IOException;

    void close() throws IOException;

    enum ACTION {UP, DOWN, LEFT, RIGHT, W, D, S, A, QUIT, SELECT}
}

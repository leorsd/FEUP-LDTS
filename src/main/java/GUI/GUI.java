package GUI;

import Model.Elements.Characters.Monster;
import Model.Elements.Characters.Player;
import Model.Elements.Key;
import Model.Elements.Trap;
import Model.Elements.Wall;
import Model.Position;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashSet;

public interface GUI {
    HashSet<ACTION> getNextAction() throws IOException;

    void drawPlayer(Player player);

    void drawWall(Wall wall);

    void drawMonster(Monster monster);

    void drawKey(Key key);

    void drawTrap(Trap trap);

    void clear();

    void refresh() throws IOException;

    void close() throws IOException;

    enum ACTION {UP, DOWN, LEFT, RIGHT, W, D, S, A, QUIT, SELECT}
}

package gui;

import model.elements.staticelements.Button;
import model.elements.dynamicelements.Monster;
import model.elements.dynamicelements.Player;
import model.elements.staticelements.Key;
import model.elements.staticelements.Trap;
import model.elements.staticelements.Wall;
import model.Position;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Set;

public interface GUI {
    Set<ACTION> getNextAction() throws IOException;

    void drawImage(Position position, BufferedImage image);

    int getGUIWidth();

    int getGUIHeight();

    void clear();

    void refresh() throws IOException;

    void close() throws IOException;

    enum ACTION {UP, DOWN, LEFT, RIGHT, W, D, S, A, QUIT, SELECT}
}

package gui;

import model.Position;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Set;

public interface GUI {
    Set<ACTION> getNextAction();

    void drawImage(Position position, BufferedImage image);

    int getGUIWidth();

    int getGUIHeight();

    void clear();

    void refresh() throws IOException;

    void close() throws IOException;

    void start() throws IOException;

    enum ACTION {UP, DOWN, LEFT, RIGHT, W, D, S, A, QUIT, SELECT}
}

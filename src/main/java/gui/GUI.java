package gui;

import model.Position;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

public interface GUI {
    Set<ACTION> getNextAction() throws IOException;

    void drawImage(Position position, BufferedImage image);

    int getGUIWidth();

    int getGUIHeight();

    void clear();

    void refresh() throws IOException;

    void close() throws IOException;

    void start() throws IOException, URISyntaxException, FontFormatException;

    enum ACTION {UP, DOWN, LEFT, RIGHT, W, D, S, A, QUIT, SELECT}
}

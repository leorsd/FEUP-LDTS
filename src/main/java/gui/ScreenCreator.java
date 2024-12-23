package gui;

import com.googlecode.lanterna.screen.Screen;

import java.awt.event.KeyListener;
import java.io.IOException;

public interface ScreenCreator {
    Screen createScreen(KeyListener keyListener)
            throws IOException;

    int getWidth();
    int getHeight();
}

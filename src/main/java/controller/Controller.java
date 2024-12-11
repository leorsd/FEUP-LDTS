package controller;

import game.GameManager;

import java.io.IOException;
import java.util.Set;
import gui.GUI;

public abstract class Controller<T> {
    private final T model;

    public Controller(T model) {
        this.model = model;
    }

    public T getModel() {
        return model;
    }

    public abstract void update(GameManager game, Set<GUI.ACTION> actions, long updateTime) throws IOException;
}

package controller.menu;

import Game.GameManager;
import Model.Scenes.LevelLoader;
import Model.Scenes.Menu;
import controller.Controller;
import GUI.GUI;

import java.io.IOException;
import java.util.Set;

public class MenuController extends Controller<Menu> {
    public MenuController(Menu menu) {
        super(menu);
    }

    @Override
    public void update(GameManager gameManager, Set<GUI.ACTION> actions, long updateTime) throws IOException {
        if (actions.size() != 1) return;
        GUI.ACTION action = actions.iterator().next();
        switch (action) {
            case UP:
                getModel().selectPreviousEntry();
                break;
            case DOWN:
                getModel().selectNextEntry();
                break;
            case SELECT:
                if (getModel().getHighlightedEntry().equals("EXIT")) {
                    gameManager.setCurrentScene(null);
                } else {
                    String level = getModel().getHighlightedEntry();
                    gameManager.setCurrentScene((new LevelLoader()).loadLevel(level));
                }
        }
    }
}

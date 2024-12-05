package controller.menu;

import game.GameManager;
import model.scenes.LevelLoader;
import model.scenes.Menu;
import controller.Controller;
import gui.GUI;

import java.io.IOException;
import java.util.Set;

public class MenuController extends Controller<Menu> {
    private long lastUpdateTime;
    public MenuController(Menu menu) {
        super(menu);
        this.lastUpdateTime = 0;
    }

    @Override
    public void update(GameManager gameManager, Set<GUI.ACTION> actions, long updateTime) throws IOException {
        if (!(actions.size() == 1)) return;

        GUI.ACTION action = actions.iterator().next();

        if(action == GUI.ACTION.SELECT){
            if (getModel().getHighlightedEntry().equals("EXIT")) {
                gameManager.setCurrentScene(null);
            } else {
                String level = getModel().getHighlightedEntry();
                gameManager.setCurrentScene( new LevelLoader().loadLevel(level));
            }
        }else {
            if (updateTime - lastUpdateTime > 200) {
                switch (action) {
                    case UP:
                        getModel().selectPreviousEntry();
                        break;
                    case DOWN:
                        getModel().selectNextEntry();
                        break;
                    default:
                        break;
                }
                lastUpdateTime = updateTime;
            }
        }
    }
}

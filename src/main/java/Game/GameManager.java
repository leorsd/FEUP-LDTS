package Game;

import Model.Scenes.Level;
import Model.Scenes.Menu;
import Model.Scenes.Scene;
import GUI.GUI;
import controller.Controller;
import controller.game.LevelController;
import controller.menu.MenuController;

import java.io.IOException;

public class GameManager {
    Scene currentScene;
    Controller<? extends Scene> controller; // Java requires keyword "extends" even though Scene is an interface
    // TODO: add scene viewer


    public GameManager(Scene currentScene) {
        this.currentScene = currentScene;
        if (currentScene instanceof Menu) {
            controller = new MenuController((Menu) currentScene);
        } else if (currentScene instanceof Level) {
            controller = new LevelController((Level) currentScene);
        }
        // TODO: initialize scene viewer as well
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public void setCurrentScene(Scene currentScene) {
        this.currentScene = currentScene;
        if (currentScene instanceof Menu) {
            controller = new MenuController((Menu) currentScene);
        } else if (currentScene instanceof Level) {
            controller = new LevelController((Level) currentScene);
        }
        // TODO: scene viewer as well
    }

    public void step(GUI gui, long currentTime) throws IOException {
        // TODO: implement call to viewer
        if (controller!=null) controller.update(this, gui.getNextAction(), currentTime);
    }
}

package game;

import controller.game.MonsterController;
import controller.game.Player1Controller;
import controller.game.Player2Controller;
import model.scenes.Level;
import model.scenes.Menu;
import model.scenes.Scene;
import gui.GUI;
import visualizer.level.LevelVisualizer;
import visualizer.menu.MenuVisualizer;
import visualizer.SceneVisualizer;
import controller.Controller;
import controller.game.LevelController;
import controller.menu.MenuController;

import java.io.IOException;

public class GameManager {
    Scene currentScene;
    Controller<? extends Scene> controller; // Java requires keyword "extends" even though Scene is an interface
    SceneVisualizer<? extends Scene> sceneVisualizer;

    public GameManager(Scene currentScene) {
        this.currentScene = currentScene;
        if (currentScene instanceof Menu) {
            controller = new MenuController((Menu) currentScene);
            sceneVisualizer = new MenuVisualizer((Menu) currentScene);
        } else if (currentScene instanceof Level) {
            controller = new LevelController((Level) currentScene, new Player1Controller((Level) currentScene), new Player2Controller((Level) currentScene), new MonsterController((Level) currentScene));
            sceneVisualizer = new LevelVisualizer((Level) currentScene);
        }else {
            throw new IllegalArgumentException("Scene must be either a Menu or a Level");
        }
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public void setCurrentScene(Scene currentScene) {
        this.currentScene = currentScene;
        if (currentScene instanceof Menu) {
            controller = new MenuController((Menu) currentScene);
            sceneVisualizer = new MenuVisualizer((Menu) currentScene);
        } else if (currentScene instanceof Level) {
            controller = new LevelController((Level) currentScene, new Player1Controller((Level) currentScene), new Player2Controller((Level) currentScene), new MonsterController((Level) currentScene));
            sceneVisualizer = new LevelVisualizer((Level) currentScene);
        }
    }

    public boolean step(GUI gui, long currentTime) throws IOException {
        if (currentScene == null) {
            return false;
        }
        if (controller!=null) controller.update(this, gui.getNextAction(), currentTime);
        if (sceneVisualizer != null) {
            sceneVisualizer.draw(gui);
        }
        return true;
    }
}

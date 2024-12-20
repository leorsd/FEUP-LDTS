package game
import model.scenes.Menu
import model.scenes.Level
import controller.Controller
import controller.menu.MenuController
import controller.game.LevelController
import visualizer.menu.MenuVisualizer
import visualizer.level.LevelVisualizer
import visualizer.SceneVisualizer
import gui.GUI
import spock.lang.Specification

class GameManagerTest extends Specification {

    def mockMenu = Mock(Menu)
    def mockLevel = Mock(Level)
    def mockController = Mock(Controller)
    def mockSceneVisualizer = Mock(SceneVisualizer)
    def mockGUI = Mock(GUI)

    def "GameManager constructor should initialize for Menu scene"() {
        when:
        def gameManager = new GameManager(mockMenu)

        then:
        gameManager.controller instanceof MenuController
        gameManager.sceneVisualizer instanceof MenuVisualizer
    }

    def "GameManager constructor should initialize for Level scene"() {
        when:
        def gameManager = new GameManager(mockLevel)

        then:
        gameManager.controller instanceof LevelController
        gameManager.sceneVisualizer instanceof LevelVisualizer
    }

    def "setCurrentScene should switch to Menu and Level scenes correctly"() {
        given:
        def gameManager = new GameManager(mockMenu)

        when:
        gameManager.setCurrentScene(mockLevel)

        then:
        gameManager.controller instanceof LevelController
        gameManager.sceneVisualizer instanceof LevelVisualizer

        when:
        gameManager.setCurrentScene(mockMenu)

        then:
        gameManager.getCurrentScene() instanceof Menu
    }

    def "step should update controller and draw scene visualizer when currentScene is not null"() {
        given:
        def gameManager = new GameManager(mockMenu)
        gameManager.controller = mockController
        gameManager.sceneVisualizer = mockSceneVisualizer

        when:
        def result = gameManager.step(mockGUI, 100L)

        then:
        1 * mockController.update(gameManager, mockGUI.getNextAction(), 100L)
        1 * mockSceneVisualizer.draw(mockGUI)
        result == true
    }

    def "step should return false if currentScene is null"() {
        given:
        def gameManager = new GameManager(mockMenu)
        gameManager.currentScene = null

        when:
        def result = gameManager.step(mockGUI, 100L)

        then:
        result == false
    }

    def "step should not update controller or draw visualizer if controller is null"() {
        given:
        def gameManager = new GameManager(mockMenu)
        gameManager.controller = null
        gameManager.sceneVisualizer = mockSceneVisualizer

        when:
        def result = gameManager.step(mockGUI, 100L)

        then:
        0 * mockController.update(_, _, _)
        1 * mockSceneVisualizer.draw(mockGUI)
        result == true
    }

    def "step should not draw scene visualizer if sceneVisualizer is null"() {
        given:
        def gameManager = new GameManager(mockMenu)
        gameManager.controller = mockController
        gameManager.sceneVisualizer = null

        when:
        def result = gameManager.step(mockGUI, 100L)

        then:
        1 * mockController.update(gameManager, mockGUI.getNextAction(), 100L)
        0 * mockSceneVisualizer.draw(mockGUI)
        result == true
    }

    def "GameManager constructor should throw exception for null scene"() {
        when:
            def gameManager = new GameManager(null)
        then:
            thrown(IllegalArgumentException)
    }
}

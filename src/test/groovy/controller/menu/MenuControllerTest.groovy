package controller.menu

import game.GameManager
import model.scenes.Level
import model.scenes.Menu
import gui.GUI
import spock.lang.Specification

class MenuControllerTest extends Specification {

    Menu menu
    GameManager gameManager
    MenuController menuController

    def setup() {
        menu = new Menu()
        gameManager = Mock(GameManager)
        menuController = new MenuController(menu)
    }

    def "should exit the game when EXIT is selected"() {
        given:
            menu.selectNextEntry()
            menu.selectNextEntry()
            menu.selectNextEntry()
            menu.selectNextEntry()
            menu.selectNextEntry()
            def actions = [GUI.ACTION.SELECT] as Set
        when:
            menuController.update(gameManager, actions, System.currentTimeMillis())
        then:
            1 * gameManager.setCurrentScene(null)
    }

    def "should load the selected level when a level entry is selected"() {
        given:
            def actions = [GUI.ACTION.SELECT] as Set
        when:
            menuController.update(gameManager, actions, System.currentTimeMillis())
        then:
            1 * gameManager.setCurrentScene(_ as Level)
    }

    def "should select previous entry when UP is pressed"() {
        given:
            menu = Mock(Menu)
            menuController = new MenuController(menu)
            menu.getHighlightedEntry() >> "Level"
            def actions = [GUI.ACTION.UP] as Set
        when:
            menuController.update(gameManager, actions, System.currentTimeMillis())
        then:
            1 * menu.selectPreviousEntry()
    }

    def "should select next entry when DOWN is pressed"() {
        given:
            menu = Mock(Menu)
            menuController = new MenuController(menu)
            menu.getHighlightedEntry() >> "Level"
            def actions = [GUI.ACTION.DOWN] as Set
        when:
            menuController.update(gameManager, actions, System.currentTimeMillis())
        then:
            1 * menu.selectNextEntry()
    }

    def "should ignore actions if there is more than one action"() {
        given:
            menu = Mock(Menu)
            menuController = new MenuController(menu)
            menu.getHighlightedEntry() >> "Level"
            def actions = [GUI.ACTION.UP, GUI.ACTION.DOWN] as Set
        when:
            menuController.update(gameManager, actions, System.currentTimeMillis())
        then:
            0 * menu.selectPreviousEntry()
            0 * menu.selectNextEntry()
    }

    def "should respect the delay between actions"() {
        given:
            menu = Mock(Menu)
            menuController = new MenuController(menu)
            menu.getHighlightedEntry() >> "Level1"
            def actions = [GUI.ACTION.UP] as Set
            long firstUpdateTime = System.currentTimeMillis()
            long secondUpdateTime = firstUpdateTime + 100
        when:
            menuController.update(gameManager, actions, firstUpdateTime)
        then:
            1 * menu.selectPreviousEntry()
        when:
            menuController.update(gameManager, actions, secondUpdateTime)
        then:
        0 * menu.selectPreviousEntry()
    }

    def "should allow action after delay"() {
        given:
            menu = Mock(Menu)
            menuController = new MenuController(menu)
            menu.getHighlightedEntry() >> "Level1"
            def actions = [GUI.ACTION.UP] as Set
            long firstUpdateTime = System.currentTimeMillis()
            long secondUpdateTime = firstUpdateTime + 250
        when:
            menuController.update(gameManager, actions, firstUpdateTime)
        then:
            1 * menu.selectPreviousEntry()
        when:
            menuController.update(gameManager, actions, secondUpdateTime)
        then:
            1 * menu.selectPreviousEntry()
    }
}
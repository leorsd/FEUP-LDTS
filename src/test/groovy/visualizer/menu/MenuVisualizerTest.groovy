package visualizer.menu

import model.Position
import spock.lang.Specification
import model.scenes.Menu
import gui.GUI

class MenuVisualizerTest extends Specification {

    def "test drawElements when menu and images are loaded successfully"() {
        given:
            def gui = Mock(GUI)
            def menu = Mock(Menu)
            menu.getEntriesSize() >> 6
            menu.getHighlightedEntryIndex() >> 2
            def menuVisualizer = new MenuVisualizer(menu)

        when:
            menuVisualizer.drawElements(gui)

        then:
            1 * gui.drawImage(_, _)
            1 * gui.drawImage(_, _)
            6 * gui.drawImage(_, _)
    }

    def "gui.drawImage is called successfully"() {
        given:
            def gui = Mock(GUI)
            gui.getGUIWidth() >> 320
            gui.getGUIHeight() >> 180
            def menu = new Menu(["Option0","Option1", "Option2"])
            menu.selectNextEntry()
            def menuVisualizer = new MenuVisualizer(menu)

        when:
            menuVisualizer.drawElements(gui)

        then:
            0 * gui.drawImage(new Position(143,75), _)
            0 * gui.drawImage(new Position(128, 75), _)
            1 * gui.drawImage(new Position(139,90), _)
            1 * gui.drawImage(new Position(143, 104), _)
            1 * gui.drawImage(new Position(128, 104), _)
            1 * gui.drawImage(new Position(143, 118), _)
    }

    def "gui.drawImage is called successfully when the Selected Entry is 0"() {
        given:
        def gui = Mock(GUI)
        gui.getGUIWidth() >> 320
        gui.getGUIHeight() >> 180
        def menu = new Menu(["Option0","Option1", "Option2"])
        def menuVisualizer = new MenuVisualizer(menu)

        when:
        menuVisualizer.drawElements(gui)

        then:
        0 * gui.drawImage(new Position(143,75), _)
        0 * gui.drawImage(new Position(128, 75), _)
        1 * gui.drawImage(new Position(139,90), _)
        1 * gui.drawImage(new Position(124,90), _)
        1 * gui.drawImage(new Position(143, 104), _)
        1 * gui.drawImage(new Position(143, 118), _)
    }
}

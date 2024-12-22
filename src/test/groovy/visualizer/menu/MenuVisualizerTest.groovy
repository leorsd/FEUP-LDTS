package visualizer.menu

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

    def "test drawElements when not enough entries in menu"() {
        given:
            def gui = Mock(GUI)
            def menu = Mock(Menu)
            menu.getEntriesSize() >> 5
            menu.getHighlightedEntryIndex() >> 0
            def menuVisualizer = new MenuVisualizer(menu)
        when:
            menuVisualizer.drawElements(gui)
        then:
            def e = thrown(IOException)
            e.message == "Not enough images for the entries of the menu"
    }
}

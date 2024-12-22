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
            1 * gui.drawImage(_, _) // Ensure drawImage is called with the menu image
            1 * gui.drawImage(_, _) // Ensure drawImage is called with the arrow image
            6 * gui.drawImage(_, _) // Ensure drawImage is called 6 times for options
    }

    def "test drawElements when not enough entries in menu"() {
        given: "Mocked GUI and Menu objects"
        def gui = Mock(GUI)
        def menu = Mock(Menu)

        // Mock behavior of the menu
        menu.getEntriesSize() >> 5 // 5 menu entries (less than 6, should trigger exception)
        menu.getHighlightedEntryIndex() >> 0 // Highlighted entry at index 0

        // Create the MenuVisualizer instance with the mocked menu
        def menuVisualizer = new MenuVisualizer(menu)

        when: "The drawElements method is called"
        menuVisualizer.drawElements(gui)

        then: "An exception is thrown due to insufficient entries"
        def e = thrown(IOException)
        e.message == "Not enough images for the entries of the menu"
    }
}

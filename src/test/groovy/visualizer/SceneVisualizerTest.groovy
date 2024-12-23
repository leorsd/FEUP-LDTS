package visualizer

import gui.GUI
import spock.lang.Specification

class SceneVisualizerTest extends Specification {

    class TestSceneVisualizer extends SceneVisualizer<Object> {
        TestSceneVisualizer(Object scene) {
            super(scene)
        }

        @Override
        protected void drawElements(GUI gui) throws IOException {
        }
    }

    def "test draw method calls correct GUI methods"() {
        given:
            def gui = Mock(GUI)
            def scene = new Object()
            def sceneVisualizer = new TestSceneVisualizer(scene)
        when:
            sceneVisualizer.draw(gui)
        then:
            1 * gui.clear()
        and:
            1 * gui.refresh()
    }

    def "test getScene returns correct scene"() {
        given:
            def scene = new Object()
        when:
            def sceneVisualizer = new TestSceneVisualizer(scene)
        then:
            sceneVisualizer.getScene() == scene
    }
}

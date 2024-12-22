package visualizer.level.dynamicelements

import gui.GUI
import model.Position
import model.elements.dynamicelements.Monster
import spock.lang.Specification
import visualizer.level.element.dynamicelements.MonsterVisualizer

class MonsterVisualizerTest extends Specification{
    def "test draw draws monster correctly based on orientation and last control count"() {
        given:
            def gui = Mock(GUI)
            def monsterVisualizer = new MonsterVisualizer()
            def monster = new Monster(new Position(20, 20),10,10,10,30)
        when:
            monster.setOrientation(Monster.ORIENTATION.LEFT)
            monster.setLastControlCount(0)
            monsterVisualizer.draw(monster, gui as GUI)
        then:
            monsterVisualizer.getSprite(monster) == "src/main/resources/images/elements/druid-left-1.png"
            1 * gui.drawImage(monster.getPosition(), _)
        when:
            monster.setOrientation(Monster.ORIENTATION.RIGHT)
            monster.setLastControlCount(0)
            monsterVisualizer.draw(monster, gui as GUI)
        then:
            monsterVisualizer.getSprite(monster) == "src/main/resources/images/elements/druid-right-1.png"
            1 * gui.drawImage(monster.getPosition(), _)
        when:
            monster.setOrientation(Monster.ORIENTATION.STANDING)
            monster.setLastControlCount(0)
            monsterVisualizer.draw(monster, gui as GUI)
        then:
            monsterVisualizer.getSprite(monster) == "src/main/resources/images/elements/druid-right-1.png"
            1 * gui.drawImage(monster.getPosition(), _)
        when:
            monster.setOrientation(Monster.ORIENTATION.LEFT)
            monster.setLastControlCount(5)
            monsterVisualizer.draw(monster, gui as GUI)
        then:
            monsterVisualizer.getSprite(monster) == "src/main/resources/images/elements/druid-left-2.png"
            1 * gui.drawImage(monster.getPosition(), _)
        when:
            monster.setOrientation(Monster.ORIENTATION.RIGHT)
            monster.setLastControlCount(5)
            monsterVisualizer.draw(monster, gui as GUI)
        then:
            monsterVisualizer.getSprite(monster) == "src/main/resources/images/elements/druid-right-2.png"
            1 * gui.drawImage(monster.getPosition(), _)
        when:
            monster.setOrientation(Monster.ORIENTATION.LEFT)
            monster.setLastControlCount(10)
            monsterVisualizer.draw(monster, gui as GUI)
        then:
            monsterVisualizer.getSprite(monster) == "src/main/resources/images/elements/druid-left-3.png"
            1 * gui.drawImage(monster.getPosition(), _)
        when:
            monster.setOrientation(Monster.ORIENTATION.RIGHT)
            monster.setLastControlCount(10)
            monsterVisualizer.draw(monster, gui as GUI)
        then:
            monsterVisualizer.getSprite(monster) == "src/main/resources/images/elements/druid-right-3.png"
            1 * gui.drawImage(monster.getPosition(), _)
    }
}
